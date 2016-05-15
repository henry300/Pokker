package pokker.client.game;

import com.google.gson.annotations.Expose;
import pokker.lib.game.player.Player;
import pokker.lib.game.table.Table;

/**
 * Represents a table on the client-side.
 */
public class TableClient extends Table<Player> {
    /**
     * Holds the id of the table. This id must match the id on the server.
     */
    @Expose
    private int id;

    TableClient(int tableSize, int bigBlind) {
        super(tableSize, bigBlind);
    }

    /**
     * @return id of the table
     */
    public int getId() {
        return id;
    }
}
