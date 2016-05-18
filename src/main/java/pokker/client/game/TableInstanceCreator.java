package pokker.client.game;

import com.google.gson.InstanceCreator;
import pokker.lib.game.table.Table;

import java.lang.reflect.Type;

public class TableInstanceCreator implements InstanceCreator<TableClient> {
    @Override
    public TableClient createInstance(Type type) {
        return new TableClient(0, 0);
    }
}
