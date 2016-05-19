package pokker.client.gui;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import pokker.lib.game.table.Action;

public class ActionButton extends VBox {
    private final Action action;

    public ActionButton(Action action, int x, int y) {
        this.action = action;
        Label textLabel = new Label(action.toString());
        this.setAlignment(Pos.BASELINE_CENTER);
        this.setTranslateX(x);
        this.setTranslateY(y);

        this.getStyleClass().add("actionButton");
        textLabel.setTextFill(Color.WHITE);
        this.setMaxSize(96, 37);
        this.setVisible(true);
        this.getChildren().add(textLabel);
    }

    public Action getAction() {
        return action;
    }
}
