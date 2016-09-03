package se.antoneliasson.kf2monitor;

import java.util.HashMap;
import java.util.Map;

public class Player {
    public final String name;
    public final String perk;
    public final String kills;

    public static final Map<String, String> formatPerk = new HashMap<>();

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

        formatPerk.put("Berserker", "BER");
        formatPerk.put("Commando", "COM");
        formatPerk.put("Support", "SUP");
        formatPerk.put("Field Medic", "MED");
        formatPerk.put("Firebug", "FB");
        formatPerk.put("Demolitionist", "DEM");
        formatPerk.put("Gunslinger", "GS");
        formatPerk.put("Sharpshooter", "SS");
        formatPerk.put("SWAT", "SWAT");
    }
}
