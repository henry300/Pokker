package pokker.client.gui;

import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import pokker.lib.game.card.Card;

import java.util.List;

public class PlayerMeCardViewerBox extends HBox{
    StackPane card1 = new StackPane();
    StackPane card2 = new StackPane();

    PlayerMeCardViewerBox() {
        this.setMaxSize(110, 73);
        this.setPrefSize(110, 73);
        this.setSpacing(5);
        this.setTranslateX(440);
        this.setTranslateY(-250);
        this.getChildren().addAll(card1, card2);
        card1.setPrefSize(50,73);
        card2.setPrefSize(50,73);
        card1.setMaxSize(50,73);
        card2.setMaxSize(50,73);
    }

    public void setCards(List<Card> cards) {
        if (cards.size() > 0) {
            card1.setStyle(cards.get(0).getCardStyle());
            card2.setStyle(cards.get(1).getCardStyle());
        } else {
//            removeCards();
        }
    }

    public void removeCards() {
        card1.setStyle(null);
        card2.setStyle(null);
    }



}
