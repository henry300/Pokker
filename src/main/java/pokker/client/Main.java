package pokker.client;

import pokker.lib.Player;
import pokker.lib.messages.Message;
import pokker.lib.messages.Request;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        ServerConnection connection = new ServerConnection();

        connection.connect("localhost", 1337);
        connection.sendMessage(new Request("getTableList"));
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
//            table.playerJoin(player);
//            player.recieveMoney(startingMoney);
//        }
//
//        // Start the new game
//        System.out.println("New game started! There are " + players.size() + " players, each of whom have " + startingMoney + "€ \n" +
//                "-------------------------------------------------------------");
//        table.gameStart();

    }
}
