package pokker.lib.network.messages;

import com.google.gson.annotations.Expose;

public class TableMessage {
    @Expose
    private final int tableId;

    public TableMessage(int tableId) {
        this.tableId = tableId;
    }

    public int getTableId() {
        return tableId;
    }
}
