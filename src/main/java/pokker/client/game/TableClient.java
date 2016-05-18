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
        if (this.playerMe != null) {
            getPlayers().remove(this.playerMe);
        }
        this.playerMe = playerMe;
        getPlayers().add(playerMe);
    }

    @Override
    public void playerJoined(Player player) {
        getPlayers().add(player);
    }

    @Override
    public void playerLeft(Player player) {
        getPlayers().remove(player);
    }

    @Override
    public void playerActed(Player player, int bet) {
        getActingPlayer().setStreetBet(bet);

        if (bet > getLargestBet()) {
            setLastPlayerOfBettingRound(getActingPlayer());
            setLargestBet(bet);
        }
    }

    public PlayerMe getPlayerMe() {
        return playerMe;
    }
}
