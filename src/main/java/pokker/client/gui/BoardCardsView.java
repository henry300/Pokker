package pokker.client.gui;

import javafx.application.Platform;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import pokker.lib.game.card.Card;
import pokker.lib.game.table.Board;

public class BoardCardsView extends HBox {
    private final Board board;

    public BoardCardsView(Board board, int x, int y) {
        setTranslateX(x);
        setTranslateY(y);
        this.board = board;
        setMaxSize(250, 73);
        setPrefSize(250, 73);
        setVisible(false);
        setSpacing(5);
    }

    private StackPane createCard(Card card) {
        StackPane fxCard = new StackPane();
        fxCard.setMaxSize(50, 73);
        fxCard.setPrefSize(50, 73);
        fxCard.setStyle(card.getCardStyle());
        fxCard.setVisible(true);
        return fxCard;
    }

    public void drawBoard() {
        Platform.runLater(() -> {
            setVisible(true);
            getChildren().clear();

            for (Card card : board.getCards()) {
                getChildren().add(createCard(card));
            }
        });
    }

    public void hideBoard() {
        setVisible(false);
    }
}
