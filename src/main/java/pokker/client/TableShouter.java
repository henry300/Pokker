package pokker.client;

import java.util.List;

public class TableShouter implements TableEventListener {
    @Override
    public void bettingRoundStarted(BettingRound round, List<Card> cardsOnTable) {
        System.out.println("--------------------" + round + " START--------------------");

        // Print current cards on table
        System.out.println("Current cards on the table:");
        for (Card card : cardsOnTable) {
            System.out.println(card.suit + " " + card.value);
        }

        System.out.println("");
    }

    @Override
    public void bettingRoundEnded(BettingRound round, int pot) {
        System.out.println("--------------------" + round + " END--------------------");
        System.out.println("There is currently " + pot + "€ in the pot.");
    }
}
