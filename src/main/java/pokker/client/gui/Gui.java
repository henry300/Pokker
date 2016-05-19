package pokker.client.gui;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import pokker.client.game.Game;
import pokker.client.game.TableClient;
import pokker.lib.game.player.Player;
import pokker.lib.game.table.Action;
import pokker.lib.network.messages.ActMessage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Gui extends Application {
    private List<GUIEventListener> eventListeners = new ArrayList<>();
    private Stage stage;
    private Game game = null;
    private StackPane menuBackgroundPane;
    private Label menuPromptLabel;
    private TableList tableList;
    private TableView tableView;

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
    public Scene newTableScene(TableClient table) {
        tableView = new TableView(game, table);
        Scene scene = new Scene(tableView);
        return scene;
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
                openTableList();
            });
        } catch (IOException e1) {
            menuPromptLabel.setText("Connection failed :(");
        }


    }

    void listen(GUIEventListener eventListener) {
        eventListeners.add(eventListener);
    }

    void removeListener(GUIEventListener eventListener) {
        eventListeners.remove(eventListener);
    }

    void dispatchEvent(GUIEventType eventType) {
        for (GUIEventListener eventListener : eventListeners) {
            eventListener.handleGUIEvent(new GUIEvent(eventType, this));
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

    public TableView getTableView() {
        return tableView;
    }

    public Game getGame() {
        return game;
    }

    public void setMenuPromptLabelText(String text) {
        menuPromptLabel.setText(text);
    }

    public void openTable(TableClient table) {
        stage.setScene(newTableScene(table));
        tableView.updatePlayersInSeats();
        dispatchEvent(GUIEventType.TABLE_DRAWN);
    }

    public void openTableList() {
        stage.setScene(getTableListScene());
        dispatchEvent(GUIEventType.TABLELIST_DRAWN);
    }
}