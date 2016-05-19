package pokker.client.gui;

import javafx.application.Platform;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import pokker.client.game.Game;
import pokker.client.game.TableClient;
import pokker.lib.game.player.Player;
import pokker.lib.game.table.Action;
import pokker.lib.network.messages.ActMessage;

import java.util.ArrayList;
import java.util.List;

public class TableView extends StackPane {
    private final Seat[] seats = new Seat[10]; // http://www.texasholdem-poker.com/images/content/position_table_a.jpg
    private final Game game;
    private final TableClient table;
    private final PlayerMeCardViewerBox playerMeCardViewerBox;
    private final ActionButton[] actionButtons;
    private final BoardCardsView boardCardsView;
    private final TextField betRaiseField;

    public TableView(Game game, TableClient table) {
        getStylesheets().addAll("styles/styles.css", "styles/tableStyles.css");
        getStyleClass().add("background");
        this.game = game;
        this.table = table;
        this.playerMeCardViewerBox = new PlayerMeCardViewerBox();
        getChildren().add(playerMeCardViewerBox);
        this.actionButtons = getActionButtons();

        boardCardsView = new BoardCardsView(table.getBoard(), 20, 0);
        getChildren().add(boardCardsView);

        betRaiseField = new TextField();
        betRaiseField.setMaxSize(50, 15);
        betRaiseField.setPrefSize(50, 15);
        betRaiseField.setTranslateX(360);
        betRaiseField.setTranslateY(208);
        betRaiseField.setVisible(false);
        getChildren().add(betRaiseField);

        addSeats();
        addPlayersChipsAndCardsHBox();

       for (ActionButton actionButton : actionButtons) {
            getChildren().add(actionButton);
        }
    }

    private void addSeats() {
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
            getChildren().add(seat);
        }

        for (Seat seat : seats) {
            seat.setVisible(true);
        }
    }

    private void addPlayersChipsAndCardsHBox() {
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
            seats[seatNr].addChipsAndCardsHBox(chipsAndCardsHBox, table);
            getChildren().add(chipsAndCardsHBox);
        }
    }

    private void removeAllPlayersFromSeats() {
        for (Seat seat : seats) {
            seat.removePlayer();
        }
    }

    public void updatePlayersInSeats() {
        Platform.runLater(() -> {
            removeAllPlayersFromSeats();
            //TODO HARDCODED TABLE ID
            List<Player> players = table.getPlayers();
            int i = 0;
            for (Player player : players) {
                seats[i].removePlayer();
                seats[i].addPlayer(player);
                seats[i].updateChipsAndCards();
                i++;
            }
        });
    }

    public void updatePlayerCardViewBox() {
        playerMeCardViewerBox.setCards(table.getPlayerMe().getHand().getCards());
    }

    public void activateSeatWithPlayer(Player player) {
        Platform.runLater(() -> {
            for (Seat seat : seats) {
                if (seat.getPlayer() == player) {
                    seat.setActive(true);
                } else {
                    seat.setActive(false);
                }
            }
        });
    }

    private ActionButton[] getActionButtons() {
        ActionButton check = new ActionButton(Action.CHECK, 430, 160);
        ActionButton call = new ActionButton(Action.CALL, 430, 160);
        ActionButton bet = new ActionButton(Action.BET, 430, 208);
        ActionButton raise = new ActionButton(Action.RAISE, 430, 208);
        ActionButton fold = new ActionButton(Action.FOLD, 430, 256);

        fold.setOnMouseReleased(e -> {
            game.getConnection().sendMessage(new ActMessage(table.getId(), -1).createContainedMessage());
        });

        check.setOnMouseReleased(e -> {
            game.getConnection().sendMessage(new ActMessage(table.getId(), table.getPlayerMe().getStreetBet()).createContainedMessage());
        });

        call.setOnMouseReleased(e -> {
            game.getConnection().sendMessage(new ActMessage(table.getId(), table.getLargestBet()).createContainedMessage());
        });

        bet.setOnMouseReleased(e -> {
            game.getConnection().sendMessage(new ActMessage(table.getId(), table.getLargestBet() * 2).createContainedMessage());
        });

        raise.setOnMouseReleased(e -> {
            game.getConnection().sendMessage(new ActMessage(table.getId(), table.getLargestBet() * 2).createContainedMessage());
        });



        return new ActionButton[]{fold, check, call, bet, raise};
    }

    public void showActionButtons() {
        Action[] allowedActions = table.getPlayerMe().getAllowedActions(table.getLargestBet());

        for (ActionButton actionButton : actionButtons) {
            for (Action allowedAction : allowedActions) {
                if (actionButton.getAction() == allowedAction) {
                    actionButton.setVisible(true);
                }
            }
        }

        betRaiseField.setVisible(true);
    }

    public void hideActionButtons() {
        for (ActionButton actionButton : actionButtons) {
            actionButton.setVisible(false);
        }

        betRaiseField.setVisible(false);
    }

    public void updateBoardCards() {
        boardCardsView.drawBoard();
    }
}
