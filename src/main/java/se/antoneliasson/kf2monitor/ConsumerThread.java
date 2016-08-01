package se.antoneliasson.kf2monitor;

import se.antoneliasson.kf2monitor.messages.GameDataContainer;
import se.antoneliasson.kf2monitor.messages.Message;
import se.antoneliasson.kf2monitor.messages.TemporaryFailure;

import java.util.Map;
import java.util.concurrent.BlockingQueue;

public class ConsumerThread extends Thread {
    private final BlockingQueue<Message> messages;
    private final Display display;

    public ConsumerThread(BlockingQueue<Message> messages, Display display) {
        this.messages = messages;
        this.display = display;
    }

    private void updateDisplay(GameDataContainer data) {
        display.hideAlert();

        Map<String, String> game = data.game;
        Map<String, String> rules = data.rules;
        display.setServerName(game.get("Server Name"));
        display.setMap(game.get("Map"));
        display.setGameType(game.get("Game type") + " " + rules.get("Difficulty"));
        display.setActive(!rules.get("Players").startsWith("0"));

        display.setWave(rules.get("Wave"));
        display.setSpectators(rules.get("Spectators"));
        display.setPlayers(rules.get("Players"));

        display.setPlayerList(data.players);
    }

    private void showFailure(TemporaryFailure failure) {
        display.showAlert();
    }

    @Override
    public void run() {
        try {
            while (!interrupted()) {
                Message m = messages.take();
                // Pattern matching! :D
                if (m instanceof GameDataContainer) {
                    GameDataContainer data = (GameDataContainer) m;
                    updateDisplay(data);
                } else if (m instanceof TemporaryFailure) {
                    TemporaryFailure failure = (TemporaryFailure) m;
                    showFailure(failure);
                }

            }
        } catch (InterruptedException e) {}
        display.shutdown();
        System.out.println("ConsumerThread shutting down");
    }
}
