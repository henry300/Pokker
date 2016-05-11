package pokker.client.game;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

/**
 * This is the class that runs the game.
 */
public class Main extends Application{
    public static void main(String[] args) throws IOException {
        // Start gui
        launch(args);



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

        System.out.println("Choose a table: ");
        int tableNum = scanner.nextInt();
        while (!game.joinTable(tables.get(tableNum - 1).getId())) {
            System.out.println("The table is already full. Choose again.");
            tableNum = scanner.nextInt();
        }

        System.out.println("You have successfully joined the table nr " + tableNum);

        // TODO: fixme
        // GSON problem: non-exposed fields are not initialized, which makes the following line throw an error.
        // tables.get(tableNum - 1).listen(new TableShouter());
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setScene(new Scene(createContent()));
        primaryStage.setWidth(1000);
        primaryStage.setHeight(615);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Poker");
        primaryStage.show();
    }

    public Parent createContent() {
        Pane root = new Pane();
        return root;
    }
}
