package se.antoneliasson.kf2monitor;

import java.util.Map;

public class Player {
    public final String name;
    public final String perk;
    public final String kills;

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", perk='" + perk + '\'' +
                ", kills='" + kills + '\'' +
                '}';
    }

    public Player(Map<String, String> fields) {
        name = fields.get("Name");
        perk = fields.get("Perk");
        kills = fields.get("Kills");

    }
}
