package pokker.client.game;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * This is the class that runs the game.
 */
public class Main {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter your name:");
        String name = scanner.next();

        Game game = new Game(name);
        System.out.println("Connecting you to the server...");

        // TODO: allow user to specify the address in the format of "ip:port"
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

        int tableNum;
        String tableNumString;

        while (true) {
            try {
                System.out.println("Choose a table(insert ONLY the number): ");
                tableNumString = scanner.next();
                tableNum = Integer.parseInt(tableNumString) ;
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid entry");
            }
        }



        while (!game.joinTable(tables.get(tableNum - 1).getId())) {
            System.out.println("The table is already full. Choose again.");
            tableNum = scanner.nextInt();
        }

        System.out.println("You have successfully joined the table nr " + tableNum);

        // TODO: fixme
        // GSON problem: non-exposed fields are not initialized, which makes the following line throw an error.
        // tables.get(tableNum - 1).listen(new TableShouter());
    }
}
