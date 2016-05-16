package pokker.lib.game.table;

/**
 * Listens to events happening on a table
 */
public interface TableEventListener<T extends Table> {
    void handleTableEvent(TableEvent<T> event);
}
