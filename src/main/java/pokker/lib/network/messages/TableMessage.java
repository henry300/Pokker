package pokker.lib.network.messages;

public class TableMessage {
    private final int tableId;

    public TableMessage(int tableId) {
        this.tableId = tableId;
    }

    public int getTableId() {
        return tableId;
    }
}
