package pokker.client.gui;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import pokker.client.game.Game;
import pokker.lib.game.card.Card;
import pokker.lib.game.card.CardSuit;
import pokker.lib.game.card.CardValue;
import pokker.lib.game.player.Player;
import pokker.lib.network.messages.ActMessage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Gui extends Application {
    Stage stage;
    Game game = null;
    StackPane menuBackgroundPane;
    Label menuPromptLabel;
    StackPane gameBackgroundPane;
    TableList tableList;
    Seat[] seats; // http://www.texasholdem-poker.com/images/content/position_table_a.jpg
    CurrentView currentView = CurrentView.INTRO;
    ActionButton[] actionButtons;

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

        textField.setOnKeyPressed(e -> {
            if (e.getCode().equals(KeyCode.ENTER)) {
                String name = textField.getText();
                if (!name.equals("")) {
                    menuBackgroundPane.getChildren().remove(questionBox);
                    connectAndCreateNewGame(name);
                }
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
        currentView = CurrentView.TABLELIST;
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

        tableList = new TableList(this);
        tableList.updateRows();

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
        currentView = CurrentView.GAMEPLAY;
        resetGameBackgroundPane();

        addSeats();
        addPlayersChipsAndCardsHBox();
        addActionButtons();


        // FOR DEMO PURPOSE ONLY
        Card card = new Card(CardSuit.SPADES, CardValue.EIGHT);
        StackPane fxCard = new StackPane();
        fxCard.setMaxSize(50, 73);
        fxCard.setStyle(card.getCardStyle());
        gameBackgroundPane.getChildren().add(fxCard);
        ///////////////////////////


        // First time update for instant results.
        updatePlayersInSeats();

        Scene scene = new Scene(gameBackgroundPane);
        return scene;
    }

    private void addActionButtons() {
        ActionButton fold = new ActionButton("Fold", -200, 330);
        ActionButton check = new ActionButton("Check", -100, 330);
        ActionButton bet = new ActionButton("Bet", 0, 330);

        fold.setOnMouseReleased(e -> {
            game.getConnection().sendMessage(new ActMessage(0, 0).createContainedMessage());
        });

        actionButtons = new ActionButton[]{fold, check, bet};

        for (ActionButton actionButton : actionButtons) {
            gameBackgroundPane.getChildren().add(actionButton);
        }
    }

    public void showActionButtons() {
        for (ActionButton actionButton : actionButtons) {
            actionButton.setVisible(true);
        }
    }

    public void hideActionButtons() {
        for (ActionButton actionButton : actionButtons) {
            actionButton.setVisible(false);
        }
    }

    /**
     * connects the user to the table and displays "Connected" or "Connection failed" depending on result.
     *
     * @param playerName
     */
    public void connectAndCreateNewGame(String playerName) {
        game = new Game(playerName);
        try {
            menuPromptLabel.setText("Connecting...");
            game.connect("localhost", 1337);
            menuPromptLabel.setText("Connected!");
            game.updateTables();
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
        seats = new Seat[10];

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

        for (Seat seat : seats) {
            seat.setVisible(true);
        }
    }

    public void addPlayersChipsAndCardsHBox() {
        List<String> allPlayerChipsAndCardsHBox = new ArrayList<>();
        allPlayerChipsAndCardsHBox.add("0 0 140 0");      // "seatNr x y"
        allPlayerChipsAndCardsHBox.add("1 -200 140 0");
        allPlayerChipsAndCardsHBox.add("2 -300 90 45");
        allPlayerChipsAndCardsHBox.add("3 -300 -75 135");
        allPlayerChipsAndCardsHBox.add("4 -200 -120 180");
        allPlayerChipsAndCardsHBox.add("5 0 -120 180");
        allPlayerChipsAndCardsHBox.add("6 200 -120 180");
        allPlayerChipsAndCardsHBox.add("7 300 -75 225");
        allPlayerChipsAndCardsHBox.add("8 300 90 315");
        allPlayerChipsAndCardsHBox.add("9 200 140 0");

        for (String coordinates : allPlayerChipsAndCardsHBox) {
            int seatNr = Integer.parseInt(coordinates.split(" ")[0]);
            int x = Integer.parseInt(coordinates.split(" ")[1]);
            int y = Integer.parseInt(coordinates.split(" ")[2]);
            int rotDegree = Integer.parseInt(coordinates.split(" ")[3]);
            PlayerChipsAndCardsHBox chipsAndCardsHBox = new PlayerChipsAndCardsHBox(x, y, rotDegree);
            //TODO HARDCODED TABLE ID
            seats[seatNr].addChipsAndCardsHBox(chipsAndCardsHBox, game.getTables().get(0));
            gameBackgroundPane.getChildren().add(chipsAndCardsHBox);
        }
    }

    public void removeAllPlayersFromSeats() {
        if (seats != null) {
            for (Seat seat : seats) {
                seat.removePlayer();
            }
        }
    }

    public void updatePlayersInSeats() {
        Platform.runLater(() -> {
            removeAllPlayersFromSeats();
            //TODO HARDCODED TABLE ID
            List<Player> players = game.getTables().get(0).getPlayers();
            int i = 0;
            for (Player player : players) {
                seats[i].removePlayer();
                seats[i].addPlayer(player);
                seats[i].updateChipsAndCards();
                i++;
            }
        });
    }

    public void activateSeatWithPlayer(Player player) {
        Platform.runLater(() -> {
            for (Seat seat : seats) {
                if(seat.getPlayer() == player) {
                    seat.setActive(true);
                } else {
                    seat.setActive(false);
                }
            }
        });
    }
}