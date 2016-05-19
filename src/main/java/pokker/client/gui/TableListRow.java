package pokker.client.gui;

import pokker.client.game.Game;
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
            TableGUIUpdater guiUpdater = new TableGUIUpdater(gui);
            gui.listen(guiUpdater);
            Game game = gui.getGame();
            game.getTableById(table.getId()).listen(guiUpdater);
            if (!game.joinTable(table.getId())) {
                gui.removeListener(guiUpdater);
                game.getTableById(table.getId()).removeListener(guiUpdater);
                gui.setMenuPromptLabelText("The table is already full. Choose again.");
                gui.openTableList();
            } else {
                gui.openTable(table);
            }
        });
    }
}
