package pokker.lib.game.table;

import com.google.gson.annotations.Expose;

public class TableEvent<T extends Table> {
    @Expose
    private final TableEventType type;

    @Expose
    private final T table;

    public TableEvent(TableEventType type, T table) {
        this.type = type;
        this.table = table;
    }

    public TableEventType getType() {
        return type;
    }

    public T getTable() {
        return table;
    }
}
