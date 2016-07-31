package se.antoneliasson.kf2monitor;

import java.util.Map;
import java.util.concurrent.BlockingQueue;

public class ConsumerThread extends Thread {
    private final BlockingQueue<GameDataContainer> messages;
    private final Display display;

    public ConsumerThread(BlockingQueue<GameDataContainer> messages, Display display) {
        this.messages = messages;
        this.display = display;
    }

    @Override
    public void run() {
        try {
            while (!interrupted()) {
                GameDataContainer message = messages.take();
                System.out.println(message);

                Map<String, String> game = message.game;
                Map<String, String> rules = message.rules;
                display.setServerName(game.get("Server Name"));
                display.setMap(game.get("Map"));
                display.setGameType(game.get("Game type") + " " + rules.get("Difficulty"));
                display.setActive(!rules.get("Players").startsWith("0"));

                display.setWave(rules.get("Wave"));
                display.setSpectators(rules.get("Spectators"));
                display.setPlayers(rules.get("Players"));
            }
        } catch (InterruptedException e) {}
        display.shutdown();
        System.out.println("ConsumerThread shutting down");
    }
}
