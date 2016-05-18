package pokker.client.gui;

import javafx.scene.layout.VBox;
import pokker.client.game.TableClient;

import java.util.ArrayList;
import java.util.List;

public class TableList extends VBox {
    List<TableListRow> tableListRows;
    Gui gui;

    public TableList(Gui gui) {
        this.setSpacing(5);
        this.setPrefWidth(400);
        this.setPrefHeight(200);
        this.setTranslateX(70);
        this.setTranslateY(180);
        this.setPrefSize(400, 30 * gui.game.getTables().size());
        this.addRows(gui);
        this.gui = gui;
    }

    private void addRows(Gui gui) {
        tableListRows = new ArrayList<>();
        int i = 0;
        for (TableClient table : gui.game.getTables()) {
            this.tableListRows.add(new TableListRow(table, i, gui));
            i++;
        }
        this.getChildren().addAll(tableListRows);
    }

    private void removeRows() {
        this.getChildren().removeAll(tableListRows);
    }

    public void updateRows() {
        removeRows();
        addRows(gui);
    }
}
