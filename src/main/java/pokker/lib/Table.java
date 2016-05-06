package pokker.lib;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a table in a poker game
 *
 * @param <PlayerT>
 */
public class Table<PlayerT extends Player> {

    @Expose
    private final List<PlayerT> players = new ArrayList<>();

    @Expose
    private final int tableSize;

    @Expose
    private final int bigBlind;

    private final int smallBlind;
    private final Deck deck = new Deck();
    private List<Card> cardsOnTable = new ArrayList<>();
    private int largestBet;
    private int pot;
    private BettingRound bettingRound = BettingRound.PREFLOP;
    private List<TableEventListener> eventListeners = new ArrayList<>();
    private boolean waitingForPlayers = true;

    public Table(int tableSize, int bigBlind) {
        this.tableSize = tableSize;
        this.bigBlind = bigBlind;
        this.smallBlind = bigBlind / 2;
    }

    public void playerJoined(PlayerT player) {
        players.add(player);

        if (waitingForPlayers && players.size() >= 2) {
            roundStart();
        }
    }

    public void playerLeft(PlayerT player) {
        players.remove(player);
    }

    public void listen(TableEventListener listener) {
        eventListeners.add(listener);
    }

    private void roundStart() {
        if (players.size() < 2) {
            waitingForPlayers = true;
            return;
        }
        waitingForPlayers = false;

        deck.shuffle();

        // Deal cards to players
        // TODO: Needs to be redone, because, in real life each player will first get one card
        // and then second, not both at the same time
        for (Player player : players) {
            player.setCards(new Card[]{deck.draw(), deck.draw()});
        }


        // small blind
        players.get(1).setStreetBet(smallBlind);

        // big blind
        players.get(2).setStreetBet(bigBlind);
        largestBet = bigBlind;

        // Street starts with player next to the big blind acting first
        bettingRoundStart(players.get(2));
    }


    private void bettingRoundStart(Player lastPlayerOfBettingRound) {
        // Deal next card/cards when necessary
        dealCardsToTable(bettingRound.getAmountOfCardsToDeal());

        // notify all listeners that a new round started
        for (TableEventListener listener : eventListeners) {
            listener.bettingRoundStarted(bettingRound, cardsOnTable);
        }

        // Assign the first player to act
        int i = players.indexOf(lastPlayerOfBettingRound) + 1;

        Player actingPlayer = players.get(i % players.size());
        while (actingPlayer != lastPlayerOfBettingRound) {

            // kui bet == 0, siis check/fold; kui placeBet > largestBet, siis raise, kui placeBet == largestBet, siis call
            int bet = actingPlayer.act(largestBet); // kontrolli üle, et bet oli õige. (serveri jaoks)
            actingPlayer.setStreetBet(bet);

            if (bet > largestBet) {
                lastPlayerOfBettingRound = actingPlayer;
                largestBet = bet;
            }

            i++;
            actingPlayer = players.get(i % players.size());

        }

        bettingRoundEnd();
    }

    private void dealCardsToTable(int amountToDeal) {
        for (int i = 0; i < amountToDeal; i++) {
            addCardToTable(deck.draw());
        }
    }

    private void bettingRoundStart() {
        bettingRoundStart(players.get(0));
    }

    private void bettingRoundEnd() {
        for (Player player : players) {
            pot += player.getStreetBet();
            player.resetStreetBet();
            largestBet = 0;
        }

        // notify all listeners that the round ended
        for (TableEventListener eventListener : eventListeners) {
            eventListener.bettingRoundEnded(bettingRound, pot);
        }

        if (bettingRound == BettingRound.RIVER) {
            roundEnd();
        } else {
            bettingRound = bettingRound.next();
            bettingRoundStart();
        }
    }

    private void roundEnd() {
        // Create bestHands list
        List<BestHand> bestHands = new ArrayList<>();
        for (Player player : players) {
            List<Card> playerAndTableCards = new ArrayList(cardsOnTable);
            playerAndTableCards.add(player.getCards()[0]);
            playerAndTableCards.add(player.getCards()[1]);
            Checker checker = new Checker(playerAndTableCards);
            Hand playerResult = checker.returnHand();//returns the hand of a player
            bestHands.add(new BestHand(player, playerResult));

        }

        // Sort bestHands and determine the noOfWinners
        Collections.sort(bestHands);
        int noOfWinners = 0;
        BestHand bestValue = bestHands.get(0);
        for (BestHand playerHand : bestHands) {
            if (playerHand.compareTo(bestValue) == 0) {
                noOfWinners = bestHands.indexOf(playerHand) + 1;
            }
        }
        //calculates no of winners
        int winningSum = pot / noOfWinners;
        System.out.println(winningSum);


        for (int i = 0; i < noOfWinners; i++) {
            bestHands.get(i).getPlayer().recieveMoney(winningSum);
        }

        //first of the list becomes last
        Collections.rotate(players, -1);

        //kicks cashless people
        for (Player player : players) {
            if (player.getMoney() < bigBlind) {
                players.remove(player);
            }
        }

        System.out.println("#########-------ROUND ENDED--------#########");

        clearTableFromCards();

        bettingRound = BettingRound.PREFLOP;
        roundStart();

    }

    private void clearTableFromCards() {
        cardsOnTable.clear();
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

    private void addCardToTable(Card card) {
        this.cardsOnTable.add(card);
    }
}
