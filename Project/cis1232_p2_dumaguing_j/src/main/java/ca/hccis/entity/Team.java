package ca.hccis.entity;

import ca.hccis.util.CisUtility;

import java.util.HashMap;
import java.util.HashSet;

public class Team {

    private static HashMap<String, Team> teamMap = new HashMap<>();
    public static HashMap<String,Team> getTeamMap(){
        return teamMap;
    }

    private String teamName;
    private String manager;
    private String coach;
    private int numberOfWins;
    private int numberOfLosses;
    private int numberOfTies;

    public Team(String teamName, String manager, String coach, int numberOfWins, int numberOfLosses, int numberOfTies) {
        this.teamName = teamName;
        this.manager = manager;
        this.coach = coach;
        this.numberOfWins = numberOfWins;
        this.numberOfLosses = numberOfLosses;
        this.numberOfTies = numberOfTies;
    }

    public Team() {
        //Default constructor
    }

    /**
     * Get details from the user
     *
     * @author cis1232
     * @since 20250118
     */
    public void getInformation() {
        this.teamName = CisUtility.getInputString("Enter team name:");
        this.manager = CisUtility.getInputString("Enter manager name:");
        this.coach = CisUtility.getInputString("Enter coach name:");
        this.numberOfWins = CisUtility.getInputInt("Enter number of wins:");
        this.numberOfLosses = CisUtility.getInputInt("Enter number of losses:");
        this.numberOfTies = CisUtility.getInputInt("Enter number of ties:");
    }

    // Getters and Setters
    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getCoach() {
        return coach;
    }

    public void setCoach(String coach) {
        this.coach = coach;
    }

    public int getNumberOfWins() {
        return numberOfWins;
    }

    public void setNumberOfWins(int numberOfWins) {
        this.numberOfWins = numberOfWins;
    }

    public int getNumberOfLosses() {
        return numberOfLosses;
    }

    public void setNumberOfLosses(int numberOfLosses) {
        this.numberOfLosses = numberOfLosses;
    }

    public int getNumberOfTies() {
        return numberOfTies;
    }

    public void setNumberOfTies(int numberOfTies) {
        this.numberOfTies = numberOfTies;
    }

    // Method to get total games played
    public int getTotalGamesPlayed() {
        return numberOfWins + numberOfLosses + numberOfTies;
    }

    public void display() {
        CisUtility.display(this.toString());
    }

    @Override
    public String toString() {
        return "\nTeam: " + teamName + "\nManager: " + manager + "\nCoach: " + coach +
                "\nWins: " + numberOfWins + "\nLosses: " + numberOfLosses + "\nTies: " + numberOfTies;
    }
}
