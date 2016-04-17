package pokker.client;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter your name:");
        String name = scanner.next();

        Game game = new Game(name);
        System.out.println("Connecting you to the server...");
        game.connect("localhost", 1337);
        System.out.println("Connected!");

        game.updateTables();

        System.out.println();
        System.out.println("Tables:");

        List<TableClient> tables = game.getTables();
        for (int i = 0; i < tables.size(); i++) {
            TableClient table = tables.get(i);
            System.out.println("Table " + (i + 1) + ": " + table.getPlayers().size() + "/" + table.getTableSize() + " players. " +
                    "Big blind: " + table.getBigBlind());
        }

        System.out.println("Choose a table: ");
        int tableNum = scanner.nextInt();
        while (!game.joinTable(tables.get(tableNum - 1).getId())) {
            System.out.println("The table is already full. Choose again.");
            tableNum = scanner.nextInt();
        }

        System.out.println("You have successfully joined the table nr " + tableNum);


        // Lots of hardcoded stuff only for demo console version.

        // TODO: move all the stuff below to the server...
//        // Hardcoded values for demo only
//        int tableSize = 9;
//        int bigBlind = 20;
//        int startingMoney = 200;
//
//        Table table = new Table(tableSize, bigBlind);
//
//        // shout out all the stuff happening on the table
//        table.listen(new TableShouter());
//
//        // Hardcode test players
//        List<Player> players = new ArrayList<>();
//        players.add(new PlayerAI("Toomas"));
//        players.add(new PlayerAI("Madis"));
//        players.add(new PlayerAI("Kaspar"));
//        players.add(new PlayerAI("Kuido"));
//        players.add(new PlayerAI("Tarvo"));
//
//        // play as Peeter
//        players.add(new PlayerMe("Peeter"));
//        // Add players to the table with starting money
//        for (Player player : players) {
//            table.playerJoined(player);
//            player.recieveMoney(startingMoney);
//        }
//
//        // Start the new game
//        System.out.println("New game started! There are " + players.size() + " players, each of whom have " + startingMoney + "€ \n" +
//                "-------------------------------------------------------------");
//        table.gameStart();

    }
}
