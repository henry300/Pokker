package pokker;

import java.util.List;

public interface TableEventListener {
    void bettingRoundStarted(BettingRound round, List<Card> cardsOnTable);
    void bettingRoundEnded(BettingRound round, int pot);
}
