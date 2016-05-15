package pokker.lib.game.table;

/**
 * Listens to events happening on a table
 */
public interface TableEventListener {
    void handleTableEvent(TableEvent event, Table table);
}
