package pokker;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Lots of hardcoded stuff only for demo console version.

        // Hardcoded values for demo only
        int tableSize = 9;
        int bigBlind = 20;
        int startingMoney = 200;

        Table table = new Table(tableSize, bigBlind);

        // Hardcode test players
        List<Player> players = new ArrayList<>();
        players.add(new Player("Toomas"));
        players.add(new Player("Madis"));
        players.add(new Player("Kaspar"));
        players.add(new Player("Peeter"));
        players.add(new Player("Kuido"));
        players.add(new Player("Tarvo"));

        // Play as Peeter
        players.get(3).setReal();


        // Add players to the table with starting money
        for (Player player : players) {
            table.playerJoin(player);
            player.recieveMoney(startingMoney);
        }

        // Start the new game
        System.out.println("New game started! There are " + players.size() + " players, each of whom have " + startingMoney + "€ \n" +
                "-------------------------------------------------------------");
        table.gameStart();

    }
}
