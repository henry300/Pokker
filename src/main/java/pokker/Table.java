package pokker;

import java.util.ArrayList;
import java.util.List;

public class Table {
    private final List<Player> players = new ArrayList<>();
    private final int tableSize;
    private final int bigBlind;
    private final Deck deck = new Deck();
    private List<Card> cardsOnTable;
    private int largestBet;
    private int pot;

    Table(int tableSize, int bigBlind) {
        this.tableSize = tableSize;
        this.bigBlind = bigBlind;
    }

    private void playerJoin() {

    }

    private void roundStart() {
        deck.shuffle();

        for (Player player : players) {
            player.setCards(new Card[]{deck.draw(), deck.draw()});
        }

        // small blind
        Player player = players.get(1);
        player.setStreetBet(bigBlind / 2);
        // big blind
        player = players.get(2);
        player.setStreetBet(bigBlind);

        pot += bigBlind / 2 + bigBlind;

        // Street starts with big blind acting first
        streetStart(player);
    }

    private void streetStart(Player lastRaised) {
        // pane kaart lauda
        int i = 0;
        Player player = null;
        while (player != lastRaised) {
            player = players.get(i % players.size());
            int bet = player.act(largestBet);
            // kontrolli üle, et placeBet oli õige (tee hiljem)

            // player saab checkida, bettida, foldida
            // kui placeBet == 0, siis check/fold; kui placeBet > largestBet, siis raise, kui placeBet == largestBet, siis call

            if (bet > largestBet) {
                lastRaised = player;
            }
        }
    }

    private void streetEnd() {
        for (Player player : players) {
            pot += player.getStreetBet();
            player.resetStreetBet();
        }
    }

    private void streetStart() {
        streetStart(players.get(1));
    }

    private void roundEnd() {
        // kontrolli, kes võitsid
        // jaga raha võitjatele (simple)
        // liiguta esimene player viimaseks (nuppude jaoks)
        // kicki mängijad, kellel pole raha
    }
}
