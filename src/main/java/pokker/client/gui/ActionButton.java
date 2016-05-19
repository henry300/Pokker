package pokker.client.gui;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class ActionButton extends VBox {
    public ActionButton(String text, int x, int y) {
        Label textLabel = new Label(text);
        this.setAlignment(Pos.BASELINE_CENTER);
        this.setTranslateX(x);
        this.setTranslateY(y);

        textLabel.setTextFill(Color.WHITE);
        this.setMaxSize(122, 52);
        this.setVisible(false);
        this.getChildren().add(textLabel);

    }
}
