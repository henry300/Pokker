package pokker.client.gui;

import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import pokker.lib.game.card.Card;
import pokker.lib.game.table.Board;

public class BoardCardsView extends HBox {
    private final Board board;

    public BoardCardsView(Board board, int x, int y) {
        this.board = board;
        setVisible(false);
    }

    private StackPane createCard(Card card) {
        StackPane fxCard = new StackPane();
        fxCard.setMaxSize(50, 73);
        fxCard.setStyle(card.getCardStyle());
        return fxCard;
    }

    public void drawBoard() {
        setVisible(true);
        getChildren().clear();

        for (Card card : board.getCards()) {
            getChildren().add(createCard(card));
        }
    }

    public void hideBoard() {
        setVisible(false);
    }
}
