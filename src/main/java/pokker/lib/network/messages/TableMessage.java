package pokker.lib.network.messages;

import com.google.gson.annotations.Expose;

public class TableMessage extends ContainableMessage {
    @Expose
    private final int tableId;

    public TableMessage(int tableId) {
        this.tableId = tableId;
    }

    public int getTableId() {
        return tableId;
    }
}
