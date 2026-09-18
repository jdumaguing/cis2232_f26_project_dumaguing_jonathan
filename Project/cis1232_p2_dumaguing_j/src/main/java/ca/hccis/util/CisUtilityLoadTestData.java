package ca.hccis.util;

import ca.hccis.entity.Goalie;
import ca.hccis.entity.Player;
import ca.hccis.entity.Team;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class CisUtilityLoadTestData {
    public static void loadTestData(HashMap<String, Team> theMap, ArrayList<Player> playerList) {

        theMap.put("Bruins", new Team("Bruins", "Liam Carter", "Ryan Blake", 7, 3, 2));
        theMap.put("Sabres", new Team("Sabres", "Noah Bennett", "Ethan Cole", 4, 8, 1));
        theMap.put("Red Wings", new Team("Red Wings", "Mason Turner", "Logan Price", 6, 5, 3));
        theMap.put("Panthers", new Team("Panthers", "Lucas Hayes", "Dylan Ward", 9, 2, 1));
        theMap.put("Canadiens", new Team("Canadiens", "Owen Brooks", "Caleb Reed", 5, 6, 4));
        theMap.put("Senators", new Team("Senators", "Jack Morgan", "Hunter Ross", 3, 7, 2));
        theMap.put("Lightning", new Team("Lightning", "Levi Foster", "Austin Gray", 8, 4, 2));
        theMap.put("Maple Leafs", new Team("Maple Leafs", "Wyatt Simmons", "Connor Shaw", 6, 5, 1));

        theMap.put("Hurricanes", new Team("Hurricanes", "Isaac Butler", "Jordan Cruz", 7, 6, 3));
        theMap.put("Blue Jackets", new Team(" Blue Jackets", "Nathan Bell", "Cameron Diaz", 2, 9, 1));
        theMap.put("Devils", new Team("Devils", "Henry Long", "Adrian Scott", 5, 5, 5));
        theMap.put("Islanders", new Team("Islanders", "Sebastian King", "Brandon Hill", 4, 7, 2));
        theMap.put("Rangers", new Team("Rangers", "Eli Cook", "Zachary Perry", 8, 3, 1));
        theMap.put("Flyers", new Team("Flyers", "Julian Hughes", "Tyler Powell", 3, 6, 4));
        theMap.put("Penguins", new Team("Penguins", "Aaron Flores", "Jason Wood", 9, 1, 2));
        theMap.put("Capitals", new Team("Capitals", "Cole Jenkins", "Dominic Barnes", 6, 4, 3));

        theMap.put("Blackhawks", new Team("Blackhawks", "Xavier Ross", "Landon Bryant", 2, 8, 2));
        theMap.put("Avalanche", new Team("Avalanche", "Hudson Price", "Miles Sanders", 10, 1, 1));
        theMap.put("Stars", new Team("Stars", "Parker Hayes", "Evan Richardson", 7, 5, 2));
        theMap.put("Wild", new Team("Wild", "Chase Howard", "Blake Peterson", 5, 6, 3));
        theMap.put("Predators", new Team("Predators", "Gavin Ramirez", "Colton James", 4, 6, 2));
        theMap.put("Blues", new Team("Blues", "Bentley Watson", "Tyson Brooks", 6, 5, 4));
        theMap.put("Jets", new Team("Jets", "Ryder Kelly", "Kai Bennett", 8, 2, 3));
        theMap.put("Utah Hockey Club", new Team("Utah Hockey Club", "Roman Hayes", "Micah Ward", 3, 9, 1));

        theMap.put("Ducks", new Team("Ducks", "Tristan Cox", "Nolan Reed", 5, 4, 6));
        theMap.put("Flames", new Team("Flames", "Malachi Ward", "Silas Carter", 7, 3, 5));
        theMap.put("Oilers", new Team("Oilers", "Jonah Brooks", "Declan Foster", 9, 2, 2));
        theMap.put("Kings", new Team("Kings", "Beckett Cruz", "Rowan Price", 6, 4, 4));
        theMap.put("Sharks", new Team("Sharks", "Asher Diaz", "Brody King", 2, 7, 3));
        theMap.put("Kraken", new Team("Kraken", "Emmett Cole", "Axel Morgan", 8, 3, 1));
        theMap.put("Canucks", new Team("Canucks", "Sawyer Scott", "Jace Bell", 5, 5, 5));
        theMap.put("Golden Knights", new Team("Golden Knights", "Finn Turner", "Zane Hill", 7, 4, 2));

        playerList.add(new Player("Connor McDavid", "Oilers", "Center", 48, 65, 18));
        playerList.add(new Player("Sidney Crosby", "Penguins", "Center", 33, 58, 22));
        playerList.add(new Player("Nathan MacKinnon", "Avalanche", "Center", 48, 70, 30));
        playerList.add(new Player("Auston Matthews", "Maple Leafs", "Center", 55, 25, 16));
        playerList.add(new Player("Leon Draisaitl", "Oilers", "Center", 44, 60, 24));

        playerList.add(new Player("Cale Makar", "Avalanche", "Defense", 18, 72, 28));
        playerList.add(new Player("Victor Hedman", "Lightning", "Defense", 14, 49, 34));
        playerList.add(new Player("Adam Fox", "Rangers", "Defense", 12, 61, 20));
        playerList.add(new Player("Roman Josi", "Predators", "Defense", 23, 63, 40));
        playerList.add(new Player("Erik Karlsson", "Sharks", "Defense", 25, 76, 36));

        playerList.add(new Player("Mitch Marner", "Maple Leafs", "Right Wing", 29, 68, 14));
        playerList.add(new Player("Artemi Panarin", "Rangers", "Left Wing", 37, 59, 12));
        playerList.add(new Player("David Pastrnak", "Bruins", "Right Wing", 48, 52, 26));
        playerList.add(new Player("Alex Ovechkin", "Capitals", "Left Wing", 41, 30, 38));
        playerList.add(new Player("Kirill Kaprizov", "Wild", "Left Wing", 39, 47, 18));

        playerList.add(new Player("Steven Stamkos", "Lightning", "Center", 34, 44, 20));
        playerList.add(new Player("Brayden Point", "Lightning", "Center", 38, 35, 16));
        playerList.add(new Player("Mark Stone", "Golden Knights", "Right Wing", 21, 43, 24));
        playerList.add(new Player("Sebastian Aho", "Hurricanes", "Center", 36, 42, 18));
        playerList.add(new Player("Dylan Larkin", "Red Wings", "Center", 32, 46, 22));

        //todo Uncomment after creating Goalie class
        playerList.add(new Goalie("Patrick Roy", "Avalanche", "Goalie", 0, 1, 2,200, 11));
        playerList.add(new Goalie("Patrick Roy", "Avalanche", "Goalie", 0, 0, 12,620, 41));

    }
}
