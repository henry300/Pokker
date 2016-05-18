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
    private PlayerMe playerMe;

    TableClient(int tableSize, int bigBlind) {
        super(tableSize, bigBlind);
    }

    public int getId() {
        return id;
    }

    void setPlayerMe(PlayerMe playerMe) {
        this.playerMe = playerMe;
    }

    public PlayerMe getPlayerMe() {
        return playerMe;
    }
}
