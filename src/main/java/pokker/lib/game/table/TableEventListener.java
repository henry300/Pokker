package pokker.lib.game.table;

/**
 * Listens to events happening on a table
 */
public interface TableEventListener {
    void bettingRoundStarted(BettingRound round, Board board);

    void bettingRoundEnded(BettingRound round, int pot);
}
