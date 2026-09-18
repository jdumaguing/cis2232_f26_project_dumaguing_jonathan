package ca.hccis;

import ca.hccis.comparator.NumberOfPenaltyMinutesComparator;
import ca.hccis.comparator.PlayerNameComparator;
import ca.hccis.entity.Goalie;
import ca.hccis.entity.Player;
import ca.hccis.entity.Team;
import ca.hccis.exception.PlayerDetailException;
import ca.hccis.util.CisUtility;
import ca.hccis.util.CisUtilityLoadTestData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

/**
 * Controls the overall flow of the program.
 * **************************************************************************************************
 * TODO 0: Background / Constraints
 * - IntelliJ, prior examples, and SAM and w3schools website and textbook can be used
 * - 150 minutes to complete
 * - AI not permitted
 * - Submission standards to be followed (10% penalty if not)
 * ---> zip contains project and is named correctly (last / first name appended to zip file name)
 * **************************************************************************************************
 *
 * @author
 * @since 2026-04
 */
public class Controller {

    public static final String EXIT = "X";

    public static final String MENU = "\n***** Main Menu *****\n1) Add a player" + System.lineSeparator()
            + "2) Show players" + System.lineSeparator()
            + EXIT + ") Exit" + System.lineSeparator();

    public static final String MESSAGE_ERROR = "Error";
    public static final String MESSAGE_EXIT = "Goodbye";
    public static final String MESSAGE_SUCCESS = "Success";

    //*********************************************************************************************
    // Data Structures
    // Note that some of these may not be used in your solution.
    //*********************************************************************************************

    private static ArrayList<Player> playerList = new ArrayList<>();


    public static void main(String[] args) {

        //***************************************************************************************
        //Note test teams are loaded into the data structures when program starts
        //***************************************************************************************
        CisUtilityLoadTestData.loadTestData(Team.getTeamMap(), playerList);
        System.out.println("*********************************************************");
        System.out.println("* CIS Team Tracking");
        System.out.println("* Note there is test data loaded into the data structures");
        System.out.println("*********************************************************");

        String menuOption;

        do {
            menuOption = CisUtility.getInputString(MENU);

            switch (menuOption.toUpperCase()) {
                case EXIT:
                    System.out.println(MESSAGE_EXIT);
                    break; //Break out of the loop as we're finished.
                case "1":
                    addPlayer();
                    break;
                case "2":
                    showPlayers();
                    break;
            }
        } while (!menuOption.equalsIgnoreCase(EXIT));
    }

    /***
     * Add new player
     *
     * @author
     * @since
     */
    public static void addPlayer() {

        try{
            String GOALIE_TYPE_QUESTION = "Is the player to be added a goalie?";
            boolean goalieBeingAdded = CisUtility.getInputBoolean(GOALIE_TYPE_QUESTION);

            if (goalieBeingAdded) {
                Goalie goalie = new Goalie();
                goalie.getInformation();
                playerList.add(goalie);
            } else {
                Player player = new Player();
                player.getInformation();
                playerList.add(player);
            }
        }catch(PlayerDetailException e){
            System.out.println(e.getMessage());
        }



    }

    /**
     * Show players
     *
     * @author
     * @since
     */
    public static void showPlayers() {

        int sortOption = CisUtility.getInputInt("How would you like to sort:" +
                "\n1: by player name" +
                "\n2: by number of penalty minutes");


        if(sortOption == 1) {
            Collections.sort(playerList, new PlayerNameComparator());
            System.out.println("Here are the players:");
            for (Player current : playerList) {
                current.display();
            }
        }
        else if(sortOption == 2) {
            Collections.sort(playerList, new NumberOfPenaltyMinutesComparator());
            System.out.println("Here are the players:");
            for (Player current : playerList) {
                current.display();
            }
        }
        else{
            System.out.println("Error");
        }



    }


}
