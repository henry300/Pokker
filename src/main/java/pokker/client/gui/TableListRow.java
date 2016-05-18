package pokker.client.gui;

import pokker.client.game.TableClient;
import pokker.client.game.TableGUIUpdater;

//TODO SEPARATE TABLE INFO STRING INTO PIECES
public class TableListRow extends javafx.scene.control.Label {

    public TableListRow(TableClient table, int i, Gui gui) {
        this.setText("Table " + (i + 1) + "                      " + table.getPlayers().size() + "/" + table.getTableSize() + " players. " +
                "                      Big blind: " + table.getBigBlind());
        this.setPrefSize(400, 30);
        this.setPadding(new javafx.geometry.Insets(0, 0, 0, 10));
        this.setTextFill(javafx.scene.paint.Color.valueOf("#ffffff"));
        this.getStyleClass().add("tableInfoText");
        this.setId("" + i);
        this.setOnMouseReleased(e -> {
            if (!gui.game.joinTable(table.getId())) {
                gui.menuPromptLabel.setText("The table is already full. Choose again.");
                gui.stage.setScene(gui.getTableListScene());
            } else {
                gui.game.getTableById(table.getId()).listen(new TableGUIUpdater(gui));
                gui.stage.setScene(gui.getTableScene());
            }
        });
    }
}
