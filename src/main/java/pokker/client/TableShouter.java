package pokker.client;

import pokker.lib.BettingRound;
import pokker.lib.Card;
import pokker.lib.TableEventListener;

import java.util.List;

/**
 * Shouts out things that are happening on the table.
 * @see pokker.lib.TableEventListener
 */
public class TableShouter implements TableEventListener {
    @Override
    public void bettingRoundStarted(BettingRound round, List<Card> cardsOnTable) {
        System.out.println("--------------------" + round + " START--------------------");

        // Print current cards on table
        System.out.println("Current cards on the table:");
        for (Card card : cardsOnTable) {
            System.out.println(card.getSuit() + " " + card.getValue());
        }

        System.out.println("");
    }

    @Override
    public void bettingRoundEnded(BettingRound round, int pot) {
        System.out.println("--------------------" + round + " END--------------------");
        System.out.println("There is currently " + pot + "€ in the pot.");
    }
}
