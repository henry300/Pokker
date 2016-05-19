package pokker.client.gui;

import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import pokker.lib.game.card.Card;

public class PlayerMeCardViewerBox extends HBox{
    StackPane card1 = new StackPane();
    StackPane card2 = new StackPane();

    PlayerMeCardViewerBox() {
        this.setMaxSize(120, 73);
        this.setPrefSize(120, 73);
        this.setSpacing(20);
        this.setTranslateX(430);
        this.setTranslateY(-250);
        this.getChildren().addAll(card1, card2);
    }

    public void setCards(Card[] cards) {
        card1.setStyle(cards[0].getCardStyle());
        card2.setStyle(cards[2].getCardStyle());
    }

    public void removeCards() {
        card1.setStyle(null);
        card2.setStyle(null);
    }



}
