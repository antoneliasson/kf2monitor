package se.antoneliasson.kf2monitor;

import org.boncey.lcdjava.LCD;
import org.boncey.lcdjava.Screen;
import org.boncey.lcdjava.StringWidget;
import org.boncey.lcdjava.TitleWidget;

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

    Display(String host, int port) {
        lcd = new LCD(host, port);

        Screen gameInfoScreen = lcd.constructScreen("game");
        gameInfoScreen.setPriority(Screen.PRIORITY_FOREGROUND);

        serverName = TitleWidget.construct(gameInfoScreen, "Server Name");
        map = StringWidget.construct(gameInfoScreen, 1, 2, "Map");
        gameType = StringWidget.construct(gameInfoScreen, 1, 3, "Game type");
        activeGame = StringWidget.construct(gameInfoScreen, 1, 4, "");

        gameInfoScreen.activate();
        serverName.activate();
        map.activate();
        gameType.activate();
        activeGame.activate();

        statusScreen = lcd.constructScreen("status");

        TitleWidget statusLabel = TitleWidget.construct(statusScreen, "Game status");
        StringWidget waveLabel = StringWidget.construct(statusScreen, 1, 2, "Wave ");
        wave = StringWidget.construct(statusScreen, 6, 2, "x/y");
        players = StringWidget.construct(statusScreen, 1, 3, "x/y");
        StringWidget playersLabel = StringWidget.construct(statusScreen, 5, 3, "players");
        spectators = StringWidget.construct(statusScreen, 1, 4, "x/y");
        StringWidget spectatorsLabel = StringWidget.construct(statusScreen, 5, 4, "spectators");

        statusScreen.activate();
        statusLabel.activate();
        waveLabel.activate();
        wave.activate();
        players.activate();
        playersLabel.activate();
        spectators.activate();
        spectatorsLabel.activate();
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
        } else {
            activeGame.setText("No players");
            statusScreen.setPriority(Screen.PRIORITY_BACKGROUND);
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
}
