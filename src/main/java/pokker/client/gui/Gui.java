package pokker.client.gui;

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
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import pokker.client.game.Game;
import pokker.client.game.TableClient;
import pokker.lib.game.player.Player;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Gui extends Application{
    Stage stage;
    Game game = null;
    StackPane menuBackgroundPane;
    Label menuPromptLabel;
    StackPane gameBackgroundPane;
    Seat[] seats = new Seat[10]; // http://www.texasholdem-poker.com/images/content/position_table_a.jpg

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        stage.setScene(getAskPlayerNameAndConnectScene());
        stage.setOnCloseRequest(e -> System.exit(0));
        stage.setWidth(1000);
        stage.setHeight(615);
        stage.setResizable(false);
        stage.setTitle("Poker");
        stage.show();
    }


    /**
     * Returns scene where user is asked for his name. After that method tries to connect to the server.
     *
     * @return Scene scene
     */
    public Scene getAskPlayerNameAndConnectScene() {
        resetMenuBackgroundPane();
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
     *
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
            Label tableInfo = getTableInfoText(table, i);
            tableInfoTexts.add(tableInfo);

            tableInfo.setOnMouseReleased(e -> {
                int tableNum = Integer.parseInt(tableInfo.getId());
                if (!game.joinTable(tables.get(tableNum).getId())) {
                    menuPromptLabel.setText("The table is already full. Choose again.");
                    stage.setScene(getTableListScene());
                } else {
                    stage.setScene(getTableScene());
                }
            });
        }
        tableList.getChildren().addAll(tableInfoTexts);


        menuBackgroundPane.getChildren().addAll(helloLabel, text, tableList);
        Scene scene = new Scene(menuBackgroundPane);
        return scene;
    }

    /**
     * Returns scene with actual Poker table and players around it. (Main gameplay scene)
     *
     * @return Scene scene
     */
    public Scene getTableScene() {
        resetGameBackgroundPane();
        addSeats();

        game.updateTables();
        addPlayersToSeats();

        Scene scene = new Scene(gameBackgroundPane);
        return scene;
    }





    // HELPER METHODS

    /**
     * connects the user to the table and displays "Connected" or "Connection failed" depending on result.
     * @param playerName
     */
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

    /**
     * Returns the lable describing the table. (tableNr, nrOfPlayers and bigBlind)
     * @param table TableClient
     * @param i (nrOfTable - 1)
     * @return
     */
    public Label getTableInfoText(TableClient table, int i) {
        Label label = new Label("Table " + (i + 1) + "                      " + table.getPlayers().size() + "/" + table.getTableSize() + " players. " +
                "                      Big blind: " + table.getBigBlind());
        label.setPrefSize(400, 30);
        label.setPadding(new Insets(0, 0, 0, 10));
        label.setTextFill(Color.valueOf("#ffffff"));
        label.getStyleClass().add("tableInfoText");
        label.setId("" + i);
        return label;
    }

    /**
     * Initializes and resets menuBackground layout. (Removes all elements except menPromptLabel)
     */
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

    /**
     * Initializes and resets Game Background layout.
     */
    public void resetGameBackgroundPane() {
        gameBackgroundPane = new StackPane();
        gameBackgroundPane.getStylesheets().addAll("styles/styles.css", "styles/tableStyles.css");
        gameBackgroundPane.getStyleClass().add("background");
    }

    /**
     * Add seats around the table.
     */
    public void addSeats() {
        // Hardcoded coordinates for a table with 10 seats.
        List<String> allSeatCoordinates = new ArrayList<>();
        allSeatCoordinates.add("0 0 230");      // "seatNr x y"
        allSeatCoordinates.add("1 -220 230");
        allSeatCoordinates.add("2 -415 130");
        allSeatCoordinates.add("3 -415 -115");
        allSeatCoordinates.add("4 -220 -210");
        allSeatCoordinates.add("5 0 -210");
        allSeatCoordinates.add("6 220 -210");
        allSeatCoordinates.add("7 415 -115");
        allSeatCoordinates.add("8 415 130");
        allSeatCoordinates.add("9 220 230");

        for (String coordinates : allSeatCoordinates) {
            int seatNr = Integer.parseInt(coordinates.split(" ")[0]);
            int x = Integer.parseInt(coordinates.split(" ")[1]);
            int y = Integer.parseInt(coordinates.split(" ")[2]);
            Seat seat = new Seat(seatNr, x, y);
            seats[seatNr] = seat;
            seat.setVisible(false);
            gameBackgroundPane.getChildren().add(seat);
        }
    }

    public void addPlayersToSeats() {
        List<Player> players = game.getTables().get(0).getPlayers();
        for (Player player : players) {
            int i = 0;
            while (seats[i].player != null) {
                i++;
            }
            seats[i].addPlayer(player);
        }
    }
}
