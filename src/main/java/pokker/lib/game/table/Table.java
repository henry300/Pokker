package pokker.lib.game.table;

import com.google.gson.annotations.Expose;
import pokker.lib.game.card.Card;
import pokker.lib.game.card.Deck;
import pokker.lib.game.hands.FullHandFactory;
import pokker.lib.game.hands.Hand;
import pokker.lib.game.player.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a table in a poker game
 *
 * @param <PlayerT>
 */
public abstract class Table<PlayerT extends Player> {
    @Expose
    private final List<PlayerT> players = new ArrayList<>();

    @Expose
    private final List<PlayerT> playersInRound = new ArrayList<>();

    @Expose
    private final int tableSize;

    @Expose
    private final int bigBlind;

    @Expose
    private PlayerT lastPlayerOfBettingRound = null;

    @Expose
    private PlayerT actingPlayer = null;
    private int actingPlayerIndex = 0;

    @Expose
    private int largestBet;

    @Expose
    private int pot;

    @Expose
    private Board board = new Board();

    @Expose
    private BettingRound bettingRound = BettingRound.PREFLOP;

    private final int smallBlind;
    private final List<TableEventListener> eventListeners;

    public Table(int tableSize, int bigBlind) {
        this.tableSize = tableSize;
        this.bigBlind = bigBlind;
        this.smallBlind = bigBlind / 2;

        this.eventListeners = new ArrayList<>();
    }

    public abstract void playerJoined(PlayerT player);
    public abstract void playerLeft(PlayerT player);
    public abstract void playerActed(PlayerT player, int bet);

    public void listen(TableEventListener listener) {
        eventListeners.add(listener);
    }

    protected void dispatchEvent(TableEventType event) {
        for (TableEventListener eventListener : eventListeners) {
            eventListener.handleTableEvent(new TableEvent(event, this));
        }
    }

    public void waitForPlayerToAct(PlayerT player) {
        setActingPlayer(player);
        dispatchEvent(TableEventType.WAITING_FOR_PLAYER_TO_ACT);
    }

    public List<PlayerT> getPlayers() {
        return players;
    }

    public int getTableSize() {
        return tableSize;
    }

    public int getBigBlind() {
        return bigBlind;
    }

    public BettingRound getBettingRound() {
        return bettingRound;
    }

    public Board getBoard() {
        return board;
    }

    public int getPot() {
        return pot;
    }

    public int getLargestBet() {
        return largestBet;
    }

    public PlayerT getActingPlayer() {
        return actingPlayer;
    }

    public int getSmallBlind() {
        return smallBlind;
    }

    protected void setLargestBet(int largestBet) {
        this.largestBet = largestBet;
    }

    protected void setLastPlayerOfBettingRound(PlayerT player) {
        lastPlayerOfBettingRound = player;
    }

    protected void setPot(int pot) {
        this.pot = pot;
    }

    protected void setNextBettingRound() {
        bettingRound = bettingRound.next();
    }

    protected void setBettingRound(BettingRound bettingRound) {
        this.bettingRound = bettingRound;
    }

    protected PlayerT getLastPlayerOfBettingRound() {
        return lastPlayerOfBettingRound;
    }

    protected void setActingPlayer(PlayerT player) {
        this.actingPlayer = player;
        actingPlayerIndex = getPlayersInRound().indexOf(player);
    }

    protected List<PlayerT> getPlayersInRound() {
        return playersInRound;
    }

    protected int getActingPlayerIndex() {
        return actingPlayerIndex;
    }

    protected void setActingPlayerIndex(int index) {
        actingPlayerIndex = index;
    }
}
