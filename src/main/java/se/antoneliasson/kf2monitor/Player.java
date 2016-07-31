package se.antoneliasson.kf2monitor;

import java.util.Map;

public class Player {
    public final String name;
    public final String perk;
    public final String dosh;

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", perk='" + perk + '\'' +
                ", dosh='" + dosh + '\'' +
                '}';
    }

    public Player(Map<String, String> fields) {
        name = fields.get("Name");
        perk = fields.get("Perk");
        dosh = fields.get("Dosh");
    }
}
