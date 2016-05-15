package pokker.lib.game.table;

import com.google.gson.annotations.Expose;
import pokker.lib.game.card.Card;
import pokker.lib.game.card.Deck;
import pokker.lib.game.hands.FullHandFactory;
import pokker.lib.game.hands.Hand;
import pokker.lib.game.player.Player;

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
    private Board board = new Board();
    private int largestBet;
    private int pot;
    private BettingRound bettingRound = BettingRound.PREFLOP;
    private List<TableEventListener> eventListeners = new ArrayList<>();
    private boolean waitingForPlayers = true;
    private FullHandFactory handFactory = new FullHandFactory();

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
            listener.bettingRoundStarted(bettingRound, board);
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
            board.add(deck.draw());
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
        List<Player> winningPlayers = determineWinningPlayers();
        distributeMoneyToWinningPlayers(winningPlayers);

        Collections.rotate(players, -1);

        // kicks cashless people
        for (Player player : players) {
            if (player.getMoney() < bigBlind) {
                players.remove(player);
            }
        }

        board.clear();
        bettingRound = BettingRound.PREFLOP;
        roundStart();
    }

    private List<Player> determineWinningPlayers() {
        Hand winningHand = handFactory.createHand(players.get(0).getHand(), board);
        List<Player> winningPlayers = new ArrayList<>();

        winningPlayers.add(players.get(0));

        for (int i = 1; i < players.size(); i++) {
            Player player = players.get(i);
            Hand hand = handFactory.createHand(player.getHand(), board);

            int compareResult = hand.compareTo(winningHand);

            if (compareResult == 1) {
                winningHand = hand;

                winningPlayers.clear();
                winningPlayers.add(player);
            } else if (compareResult == 0) {
                winningPlayers.add(player);
            }
        }

        return winningPlayers;
    }

    private void distributeMoneyToWinningPlayers(List<Player> winningPlayers) {
        int winningSum = pot / winningPlayers.size();

        for (Player winningPlayer : winningPlayers) {
            winningPlayer.recieveMoney(winningSum);
        }
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

    public Deck getDeck() {
        return deck;
    }
}
