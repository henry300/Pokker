package pokker.client.game;

import pokker.lib.game.card.Card;
import pokker.lib.game.table.*;

/**
 * Shouts out things that are happening on the table.
 *
 * @see TableEventListener
 */
public class TableShouter implements TableEventListener {
    @Override
    public void handleTableEvent(TableEvent event) {
        Table table = event.getTable();
        switch (event.getType()) {
            case BETTING_ROUND_START:
                printBettingRoundStart(table.getBettingRound(), table.getBoard());
                break;
            case BETTING_ROUND_END:
                printBettingRoundEnd(table.getBettingRound(), table.getPot());
                break;
        }
    }

    private void printBettingRoundStart(BettingRound round, Board board) {
        System.out.println("--------------------" + round + " START--------------------");

        // Print current cards on table
        System.out.println("Current cards on the table:");
        for (Card card : board.getCards()) {
            System.out.println(card.toString());
        }

        System.out.println("");
    }

    public void printBettingRoundEnd(BettingRound round, int pot) {
        System.out.println("--------------------" + round + " END--------------------");
        System.out.println("There is currently " + pot + "€ in the pot.");
    }

}
