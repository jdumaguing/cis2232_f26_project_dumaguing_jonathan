package ca.hccis.comparator;

import ca.hccis.entity.Player;

import java.util.Comparator;

public class PlayerNameComparator implements Comparator<Player> {
    @Override
    public int compare(Player p1, Player p2) {

        int playerNameCompareValue = p1.getPlayerName().compareTo(p2.getPlayerName());
        return playerNameCompareValue;


    }
}
