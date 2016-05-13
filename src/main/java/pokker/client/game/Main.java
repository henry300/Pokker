package pokker.client.game;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

/**
 * This is the class that runs the game.
 */
public class Main extends Application{
    Stage stage;
    Game game = new Game();

    public static void main(String[] args) throws IOException {
        // Start gui
        launch(args);

        // Prevent other code from running (temporary)
        System.exit(0);


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
        stage = primaryStage;
        stage.setScene(getMenuScene());
        stage.setWidth(1000);
        stage.setHeight(615);
        stage.setResizable(false);
        stage.setTitle("Poker");
        stage.show();
    }


    public Scene getMenuScene() {
        StackPane background = new StackPane();
        background.getStylesheets().addAll("styles/styles.css", "styles/menuStyles.css");
        background.getStyleClass().add("background");
        Scene scene = new Scene(background);

        if (game.getPlayerName() == null) {
            askPlayerName(background);
        }


        return scene;
    }

    public Scene getTableScene() {
        Region background = new Region();
        background.getStylesheets().addAll("styles/styles.css", "styles/tableStyles.css");
        background.getStyleClass().add("background");
        Scene scene = new Scene(background);

        return scene;
    }

    public void askPlayerName(Pane background) {
        Group questionBox = new Group();

        // Create and style textField
        TextField textField = new TextField();
        textField.setPromptText("Please enter your name");
        textField.getStyleClass().add("nameSubmitTextField");
        textField.setPrefWidth(300);
        textField.setPrefHeight(30);

        // Create and style button
        Button button = new Button();
        button.setPrefWidth(300);
        button.setPrefHeight(30);
        button.setText("OK");
        button.getStyleClass().add("nameSubmitButton");
        button.setTranslateY(40);

        questionBox.getChildren().addAll(textField, button);
        questionBox.setTranslateX(-300);
        background.getChildren().add(questionBox);

        // Act when submit button is clicked
        button.setOnMouseReleased(e -> {
            String name = textField.getText();
            if (!name.equals("")) {
                game.setPlayerName(name);
                background.getChildren().remove(questionBox);
            }
        });
    }
}
