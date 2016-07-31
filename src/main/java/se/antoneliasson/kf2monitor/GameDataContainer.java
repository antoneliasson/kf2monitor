package se.antoneliasson.kf2monitor;

import java.util.List;
import java.util.Map;

public class GameDataContainer {
    @Override
    public String toString() {
        return "GameDataContainer{" +
                "game=" + game +
                ", rules=" + rules +
                ", players=" + players +
                '}';
    }

    public final Map<String, String> game;
    public final Map<String, String> rules;
    public final List<Player> players;

    public GameDataContainer(Map<String, String> game, Map<String, String> rules, List<Player> players) {
        this.game = game;
        this.rules = rules;
        this.players = players;
    }
}
