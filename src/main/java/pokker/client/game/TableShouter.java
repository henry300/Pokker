package pokker.client.game;

import pokker.lib.game.card.Card;
import pokker.lib.game.table.BettingRound;
import pokker.lib.game.table.Board;
import pokker.lib.game.table.TableEventListener;

/**
 * Shouts out things that are happening on the table.
 *
 * @see TableEventListener
 */
public class TableShouter implements TableEventListener {
    @Override
    public void bettingRoundStarted(BettingRound round, Board board) {
        System.out.println("--------------------" + round + " START--------------------");

        // Print current cards on table
        System.out.println("Current cards on the table:");
        for (Card card : board.getCards()) {
            System.out.println(card.toString());
        }

        System.out.println("");
    }

    @Override
    public void bettingRoundEnded(BettingRound round, int pot) {
        System.out.println("--------------------" + round + " END--------------------");
        System.out.println("There is currently " + pot + "€ in the pot.");
    }
}
