package pokker.client.game;

import com.google.gson.annotations.Expose;
import pokker.lib.game.player.Player;
import pokker.lib.game.table.Table;
import pokker.lib.game.table.TableEventType;

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
    private boolean cardsDealt = false;

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
        dispatchEvent(TableEventType.PLAYER_JOINED);
    }

    @Override
    public void playerLeft(Player player) {
        getPlayers().remove(player);
        dispatchEvent(TableEventType.PLAYER_LEFT);
    }

    @Override
    public void playerActed(Player player, int bet) {
        getActingPlayer().setStreetBet(bet);

        if (bet > getLargestBet()) {
            setLastPlayerOfBettingRound(getActingPlayer());
            setLargestBet(bet);
        } else if (bet < getLargestBet()) {
            getPlayersInRound().remove(player);
        }

        dispatchEvent(TableEventType.PLAYER_ACTED);
    }

    public PlayerMe getPlayerMe() {
        return playerMe;
    }

    public void roundStart() {
        cardsDealt = true;
        getPlayersInRound().clear();
        getPlayersInRound().addAll(getPlayers());
    }

    public void roundEnd() {
        cardsDealt = false;
    }

    public void bettingRoundStart() {
        dispatchEvent(TableEventType.BETTING_ROUND_START);
    }

    public void bettingRoundEnd() {
        for (Player player : getPlayers()) {
            setPot(getPot() + player.getStreetBet());
            player.setStreetBet(0);
        }

        setLargestBet(0);
        dispatchEvent(TableEventType.BETTING_ROUND_END);
    }

    public boolean areCardsDealt() {
        return cardsDealt;
    }
}
