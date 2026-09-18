package ca.hccis.entity;

import ca.hccis.exception.PlayerDetailException;
import ca.hccis.util.CisUtility;

public class Goalie extends Player {

    protected int shotsAgainst;
    protected int savesMade;
    protected double goalieSavePercentage;

    public Goalie() {
    }

    public Goalie(String playerName, String teamName, String position,
                  int goals, int assists, int penaltyMinutes, int shotsAgainst, int savesMade) {
        super(playerName, teamName, position, goals, assists, penaltyMinutes);
        this.shotsAgainst = shotsAgainst;
        this.savesMade = savesMade;
    }

    public int getShotsAgainst() {
        return shotsAgainst;
    }

    public void setShotsAgainst(int shotsAgainst) {
        this.shotsAgainst = shotsAgainst;
    }

    public int getSavesMade() {
        return savesMade;
    }

    public void setSavesMade(int savesMade) {
        this.savesMade = savesMade;
    }

    @Override
    public void getInformation() throws PlayerDetailException {
        super.getInformation();
        shotsAgainst = CisUtility.getInputInt("Shots Against: ");
        savesMade = CisUtility.getInputInt("Saves Made: ");
        goalieSavePercentage = (double) savesMade / shotsAgainst;
    }

    public void display() {
        System.out.println(this.toString());
    }

    @Override
    public String toString() {
        String output = "\nShots Against: " + shotsAgainst +
                "\nSaves Made: " + savesMade +
                "\nGoalie's Save Percentage: " + goalieSavePercentage;
        return super.toString() + output;
    }
}
