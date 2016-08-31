package se.antoneliasson.kf2monitor;

import org.boncey.lcdjava.LCD;
import org.boncey.lcdjava.Screen;
import org.boncey.lcdjava.StringWidget;
import org.boncey.lcdjava.TitleWidget;

import java.util.List;

class Display {
    private final LCD lcd;

    private final TitleWidget serverName;
    private final StringWidget map;
    private final StringWidget gameType;
    private final StringWidget activeGame;
    private final StringWidget wave;
    private final StringWidget players;
    private final StringWidget spectators;
    private final Screen statusScreen;
    private final Screen playersScreen;
    private final StringWidget player[];

    private final Screen alertScreen;

    Display(String host, int port) {
        lcd = new LCD(host, port);

        Screen gameInfoScreen = lcd.constructScreen("game", Screen.PRIORITY_FOREGROUND, true);

        serverName = TitleWidget.construct(gameInfoScreen, "Server Name");
        map = StringWidget.construct(gameInfoScreen, 1, 2, "Map");
        gameType = StringWidget.construct(gameInfoScreen, 1, 3, "Game type");
        activeGame = StringWidget.construct(gameInfoScreen, 1, 4, "");

        serverName.activate();
        map.activate();
        gameType.activate();
        activeGame.activate();

        statusScreen = lcd.constructScreen("status", Screen.PRIORITY_HIDDEN, true);

        TitleWidget statusLabel = TitleWidget.construct(statusScreen, "Game status");
        StringWidget waveLabel = StringWidget.construct(statusScreen, 1, 2, "Wave ");
        wave = StringWidget.construct(statusScreen, 6, 2, "x/y");
        players = StringWidget.construct(statusScreen, 1, 3, "x/y");
        StringWidget playersLabel = StringWidget.construct(statusScreen, 5, 3, "players");
        spectators = StringWidget.construct(statusScreen, 1, 4, "x/y");
        StringWidget spectatorsLabel = StringWidget.construct(statusScreen, 5, 4, "spectators");

        statusLabel.activate();
        waveLabel.activate();
        wave.activate();
        players.activate();
        playersLabel.activate();
        spectators.activate();
        spectatorsLabel.activate();

        playersScreen = lcd.constructScreen("players", Screen.PRIORITY_HIDDEN, true);
        TitleWidget playersTitle = TitleWidget.construct(playersScreen, "Top 3 players");
        player = new StringWidget[3];
        for (int i = 0; i < 3; i++) {
            player[i] = StringWidget.construct(playersScreen, 1, i+2, "");
        }
        playersTitle.activate();
        for (int i = 0; i < 3; i++) {
            player[i].activate();
        }

        alertScreen = lcd.constructScreen("alert", Screen.PRIORITY_HIDDEN, true);
        TitleWidget alertTitle = TitleWidget.construct(alertScreen, "Alert");
        StringWidget alertM1 = StringWidget.construct(alertScreen, 1, 2, "Error connecting to");
        StringWidget alertM2 = StringWidget.construct(alertScreen, 1, 3, "WebAdmin. Retrying..");
        alertTitle.activate();
        alertM1.activate();
        alertM2.activate();
    }

    void shutdown() {
        lcd.shutdown();
    }

    void setServerName(String str) {
        serverName.setText(str);
    }

    void setMap(String str) {
        map.setText(str);
    }

    void setGameType(String str) {
        gameType.setText(str);
    }

    void setActive(boolean active) {
        if (active) {
            activeGame.setText("In game");
            statusScreen.setPriority(Screen.PRIORITY_FOREGROUND);
            playersScreen.setPriority(Screen.PRIORITY_FOREGROUND);
        } else {
            activeGame.setText("No players");
            statusScreen.setPriority(Screen.PRIORITY_HIDDEN);
            playersScreen.setPriority(Screen.PRIORITY_HIDDEN);
        }
    }

    void setWave(String str) {
        wave.setText(str);
    }

    void setPlayers(String str) {
        players.setText(str);
    }

    void setSpectators(String str) {
        spectators.setText(str);
    }

    void setPlayerList(List<Player> players) {
        for (int i = 0; i < 3; i++) {
            if (i < players.size()) {
                Player p = players.get(i);
                player[i].setText(p.kills + " " + Player.formatPerk.get(p.perk) + " " + p.name);
            } else {
                player[i].setText("");
            }
        }
    }

    void showAlert() {
        alertScreen.setPriority(Screen.PRIORITY_ALERT);
    }

    void hideAlert() {
        alertScreen.setPriority(Screen.PRIORITY_HIDDEN);
    }
}
