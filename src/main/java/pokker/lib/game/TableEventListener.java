package pokker.lib.game;

import java.util.List;

/**
 * Listens to events happening on a table
 */
public interface TableEventListener {
    void bettingRoundStarted(BettingRound round, List<Card> cardsOnTable);

    void bettingRoundEnded(BettingRound round, int pot);
}
