package ca.hccis.entity;

import ca.hccis.exception.PlayerDetailException;
import ca.hccis.util.CisUtility;

import java.util.HashMap;
import java.util.HashSet;

public class Player {

    private String playerName = "";
    private String teamName;
    private String position;
    private int goals;
    private int assists;
    private int penaltyMinutes;

    //*********************************************************************************************
    // Static set to hold distinct positions for hockey players
    //*********************************************************************************************
    private static HashSet<String> positionSet = new HashSet<>();

    public static HashSet<String> getPositionSet() {
        return positionSet;
    }

    // Constructor
    public Player(String playerName, String teamName, String position,
                        int goals, int assists, int penaltyMinutes) {
        this.playerName = playerName;
        this.teamName = teamName;
        this.position = position;
        this.goals = goals;
        this.assists = assists;
        this.penaltyMinutes = penaltyMinutes;
    }

    public Player() {
        // Default constructor
    }

    /**
     * Get player information from the user
     * - If the position already exists in positionSet,
     *   notify the user but still assign it.
     * - Add position to positionSet
     */
    public void getInformation() throws PlayerDetailException {

        //Loading allowed positions into the positionSet
        positionSet.add("Center");
        positionSet.add("Left Wing");
        positionSet.add("Right Wing");
        positionSet.add("Defence");
        positionSet.add("Goalie");

        this.playerName = CisUtility.getInputString("Enter player name:");
        this.teamName = CisUtility.getInputString("Enter team name:");

        String positionPrompt = "Position (";
        for(String current: positionSet){
            positionPrompt = positionPrompt +" ";
        }
        positionPrompt = positionPrompt + "):";

        this.position = CisUtility.getInputString(positionPrompt);

        if(!(positionSet.contains(this.position))){
            throw new PlayerDetailException("Position is not allowed");
        }

        this.goals = CisUtility.getInputInt("Enter number of goals:");

        this.assists = CisUtility.getInputInt("Enter number of assists:");

        this.penaltyMinutes = CisUtility.getInputInt("Enter penalty minutes:");
    }

    // Getters and Setters
    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getGoals() {
        return goals;
    }

    public void setGoals(int goals) {
        this.goals = goals;
    }

    public int getAssists() {
        return assists;
    }

    public void setAssists(int assists) {
        this.assists = assists;
    }

    public int getPenaltyMinutes() {
        return penaltyMinutes;
    }

    public void setPenaltyMinutes(int penaltyMinutes) {
        this.penaltyMinutes = penaltyMinutes;
    }

    // Method to calculate total points
    public int getTotalPoints() {
        return goals + assists;
    }

    public void display() {
        CisUtility.display(this.toString());
    }

    @Override
    public String toString() {

        //This map has key=team name and value = Team object for all teams.
        //It is already loaded with values.
        HashMap<String, Team> teamMap = Team.getTeamMap();
        String coach = teamMap.get(this.teamName).getCoach();


        return "\nPlayer: " + playerName +
                "\nTeam: " + teamName +
                "\nCoach: " + coach +
                "\nPosition: " + position +
                "\nGoals: " + goals +
                "\nAssists: " + assists +
                "\nPenalty Minutes: " + penaltyMinutes +
                "\nTotal Points: " + getTotalPoints();
    }
}