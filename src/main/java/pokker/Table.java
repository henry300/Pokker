package pokker;

import java.util.ArrayList;
import java.util.Collections;
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
        List<BestHand> bestHands = new ArrayList<>();
        for (Player player:players) {
            List<Card> playerAndTableCards = new ArrayList(cardsOnTable);
            playerAndTableCards.add(player.getCards()[0]);
            playerAndTableCards.add(player.getCards()[1]);
            Checker checker = new Checker(playerAndTableCards);
            String playerResult = checker.returnHand();//returns code for the hand ("BA" etc)
            bestHands.add(new BestHand(playerResult,player));

        }
        Collections.sort(bestHands);
        int noOfWinners=0;
        String bestValue = bestHands.get(0).value;
        for (BestHand playerHand:bestHands) {
            if(playerHand.value.equals(bestValue)){
                noOfWinners = bestHands.indexOf(playerHand)+1;
            }
        }

        //calculates no of winners
        int winningSum = pot/noOfWinners;
        for (int i = 0; i < noOfWinners; i++) {
            bestHands.get(i).player.recieveMoney(winningSum);
        }

        //first of the list becomes last
        Collections.rotate(players, -1);

        //kicks cashless people
        for (Player player:players) {
            if(player.getMoney()<bigBlind){
                players.remove(player);
            }
        }

        // kontrolli, kes võitsid
        // jaga raha võitjatele (simple)
        // liiguta esimene player viimaseks (nuppude jaoks)
        // kicki mängijad, kellel pole raha
    }
}
