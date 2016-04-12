package pokker.client;

import com.google.gson.annotations.Expose;
import pokker.lib.Table;

public class TableClient extends Table {
    @Expose
    private int id;

    TableClient(int tableSize, int bigBlind) {
        super(tableSize, bigBlind);
    }

    int getId() {
        return id;
    }
}
