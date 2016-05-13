package pokker.client.game;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * This is the class that runs the game.
 */
public class Main extends Application{
    Stage stage;
    Game game = null;
    StackPane menuBackgroundPane;
    Label menuPromptLabel;

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
        stage.setScene(getAskPlayerNameAndConnectScene());
        stage.setWidth(1000);
        stage.setHeight(615);
        stage.setResizable(false);
        stage.setTitle("Poker");
        stage.show();
    }

    /**
     * Returns scene where user is asked for his name. After that method tries to connect to the server.
     * @return Scene scene
     */
    public Scene getAskPlayerNameAndConnectScene() {
        resetMenuBackgroundPane();

        // Initialize menu prompt message label


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
        menuBackgroundPane.getChildren().add(questionBox);

//         Try to connect when submit button is clicked
        button.setOnMouseReleased(e -> {
            String name = textField.getText();
            if (!name.equals("")) {
                menuBackgroundPane.getChildren().remove(questionBox);
                connectAndCreateNewGame(name);
            }
        });

        Scene scene = new Scene(menuBackgroundPane);
        return scene;
    }

    /**
     * Returns the scene where user can select, which table he wants to join
     * @return Scene scene
     */
    public Scene getTableListScene() {
        resetMenuBackgroundPane();

        Label helloLabel = new Label();
        helloLabel.getStyleClass().addAll("menuPromptLabel", "h1");
        helloLabel.setTranslateX(-350);
        helloLabel.setTranslateY(-200);
        helloLabel.setText("Hello " + game.getPlayerName() + "!");

        Label text = new Label();
        text.getStyleClass().addAll("menuPromptLabel", "p");
        text.setTranslateX(-300);
        text.setTranslateY(-150);
        text.setText("Choose a table you want to join:");


        game.updateTables();

//        Group tableList = new Group();
//        tableList.prefWidth(500);
//        tableList.setTranslateX(-230);
//        tableList.setTranslateY(-90);
//
//
//        HBox tableInfo = new HBox();
//        box.setPrefSize(400, 30);
//        box.getStyleClass().add("tableHBox");
//
//        Label tableInfoText = new Label("See on table 1");
//        box.getChildren().addAll(tableInfo);
//
//        tableList.getChildren().addAll(box);

        List<TableClient> tables = game.getTables();

        VBox tableList = new VBox(5);
        tableList.setPrefWidth(400);
        tableList.setPrefHeight(200);
        tableList.setTranslateX(70);
        tableList.setTranslateY(180);
        tableList.setPrefSize(400, 30 * tables.size());



        List<Label> tableInfoTexts = new ArrayList<>();

        for (int i = 0; i < tables.size(); i++) {
            TableClient table = tables.get(i);
            tableInfoTexts.add(getTableInfoText(table, i));
        }
        tableList.getChildren().addAll(tableInfoTexts);


        menuBackgroundPane.getChildren().addAll(helloLabel, text, tableList);
        Scene scene = new Scene(menuBackgroundPane);
        return scene;
    }

    public Label getTableInfoText(TableClient table, int i) {
        Label label = new Label("Table " + (i + 1) + "                      " + table.getPlayers().size() + "/" + table.getTableSize() + " players. " +
                "                      Big blind: " + table.getBigBlind());
        label.setPrefSize(400, 30);
        label.setPadding(new Insets(0, 0, 0, 10));
        label.setTextFill(Color.valueOf("#ffffff"));
        label.getStyleClass().add("tableInfoText");
        return label;
    }

    /**
     * Returns scene with acutal Poker table and players around it. (Main scene)
     * @return Scene scene
     */
    public Scene getTableScene() {
        Region background = new Region();
        background.getStylesheets().addAll("styles/styles.css", "styles/tableStyles.css");
        background.getStyleClass().add("menuBackgroundPane");
        Scene scene = new Scene(background);

        return scene;
    }

    // HELPER METHODS
    public void connectAndCreateNewGame(String playerName) {
        game = new Game(playerName);
        try {
            menuPromptLabel.setText("Connecting...");
            game.connect("localhost", 1337);
            menuPromptLabel.setText("Connected!");
            final Timeline timeline = new Timeline();
            timeline.getKeyFrames().add(new KeyFrame(Duration.millis(600),
                    new KeyValue(menuPromptLabel.textProperty(), "")));
            timeline.play();
            timeline.setOnFinished(event -> {
                stage.setScene(getTableListScene());
            });
        } catch (IOException e1) {
            menuPromptLabel.setText("Connection failed :(");
        }


    }
    public void resetMenuBackgroundPane() {
        menuBackgroundPane = new StackPane();
        menuBackgroundPane.getStylesheets().addAll("styles/styles.css", "styles/menuStyles.css");
        menuBackgroundPane.getStyleClass().add("background");

        // Add prompt message possibility to Menu
        menuPromptLabel = new Label();
        menuPromptLabel.getStyleClass().addAll("menuPromptLabel", "h2");
        menuPromptLabel.setTranslateX(-300);

        menuBackgroundPane.getChildren().add(menuPromptLabel);

    }
}
