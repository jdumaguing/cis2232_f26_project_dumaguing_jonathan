package ca.hccis.comparator;

import ca.hccis.entity.Player;

import java.util.Comparator;

public class NumberOfPenaltyMinutesComparator implements Comparator<Player> {
    @Override
    public int compare(Player o1, Player o2) {
        return o1.getPenaltyMinutes() - o2.getPenaltyMinutes();
    }
}
