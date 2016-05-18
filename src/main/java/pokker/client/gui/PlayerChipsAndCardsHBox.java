package pokker.client.gui;

import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import pokker.client.game.TableClient;

class PlayerChipsAndCardsHBox extends HBox {
    StackPane cardsRect = new StackPane();
    StackPane chipsRect = new StackPane();

    public PlayerChipsAndCardsHBox(int x, int y, int rotDegree) {
        this.setTranslateX(x);
        this.setTranslateY(y);
        this.setPrefSize(80, 40);
        this.setMaxSize(80, 40);
        this.setRotate(rotDegree);
        this.cardsRect.setMaxSize(40, 40);
        this.chipsRect.setMaxSize(40, 40);
        this.cardsRect.setPrefSize(40, 40);
        this.chipsRect.setPrefSize(40, 40);
        this.getChildren().addAll(cardsRect, chipsRect);
    }

    public void updateChipsRectImage(int money, int bigBlind) {
        if (money > 0) {
            this.chipsRect.setStyle("-fx-background-image: url(images/chips/1500.png)");
        } else {
            this.chipsRect.setStyle(null);
        }
    }

    public void updateCardsRectImage(boolean hasCards) {
        if (hasCards) {
            this.cardsRect.setStyle("-fx-background-image: url(images/cards/cardBacks.png)");
        } else {
            this.cardsRect.setStyle(null);
        }
    }

    public void update(int money, TableClient table, boolean hasCards) {
        updateChipsRectImage(money, table.getBigBlind());
        updateCardsRectImage(hasCards);

    }


}