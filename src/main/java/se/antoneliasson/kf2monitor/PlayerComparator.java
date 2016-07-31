package se.antoneliasson.kf2monitor;

/**
 * Compares players by kills
 */
class PlayerComparator implements java.util.Comparator {
    @Override
    public int compare(Object o1, Object o2) {
        Player p1 = (Player) o1;
        Player p2 = (Player) o2;
        return Integer.parseInt(p2.kills) - Integer.parseInt(p1.kills);
    }
}
