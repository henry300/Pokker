package pokker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Table {
    private final List<Player> players = new ArrayList<>();
    private final int tableSize;
    private final int bigBlind;
    private final int smallBlind;
    private final Deck deck = new Deck();
    private List<Card> cardsOnTable = new ArrayList<>();
    private int largestBet;
    private int pot;
    private Dealer dealer;
    private int bettingRound = 1;

    Table(int tableSize, int bigBlind) {
        this.tableSize = tableSize;
        this.bigBlind = bigBlind;
        this.smallBlind = bigBlind / 2;
        dealer = new Dealer(this);
    }

    public void playerJoin(Player player) {
        players.add(player);
    }

    public void gameStart() {
        roundStart();
    }

    private void roundStart() {
        if (players.size() < 1){
            return;
        }

        dealer.shuffleDeck();
        dealer.drawCardsToPlayers();

        // small blind
        Player player = players.get(1);
        player.setStreetBet(smallBlind);
        // big blind
        player = players.get(2);
        player.setStreetBet(bigBlind);
        largestBet = bigBlind;

        // Street starts with player next to the big blind acting first
        streetStart(player);
    }

    private void streetStart(Player lastRaised) {
        System.out.println("--------------------BETTINGROUND NR " + bettingRound + " START--------------------");

        // Deal next card/cards when necessary
        dealer.dealNextCards();

        // Print current cards on table
        System.out.println("Current cards on the table:");
        for (Card card : cardsOnTable) {
            System.out.println(card.suit + " " + card.value);
        }
        System.out.println("");

        // Assign the first player to act
        int i = players.indexOf(lastRaised) + 1;

        Player actingPlayer = players.get(i % players.size());
        while (actingPlayer != lastRaised) {

            // kui bet == 0, siis check/fold; kui placeBet > largestBet, siis raise, kui placeBet == largestBet, siis call
            int bet = dealer.askPlayerToAct(actingPlayer);  // kontrolli üle, et bet oli õige. (serveri jaoks)

            if (bet > largestBet) {
                lastRaised = actingPlayer;
                largestBet = bet;
            }

            i++;
            actingPlayer = players.get(i % players.size());

        }

        streetEnd();
    }

    public void setCardsOnTable(List<Card> cardsOnTable) {
        this.cardsOnTable = cardsOnTable;
    }

    private void streetStart() {
        streetStart(players.get(0));
    }

    private void streetEnd() {
        System.out.println("--------------------BETTINGROUND NR " + bettingRound + " END--------------------");
        for (Player player : players) {
            pot += player.getStreetBet();
            player.resetStreetBet();
            largestBet = 0;
        }
        System.out.println("There is currently " + pot + "€ in the pot.");

        if (bettingRound < 4) {
            bettingRound += 1;
            streetStart();
        } else {
            roundEnd();
        }
    }

    private void roundEnd() {
        // Create bestHands list
        List<BestHand> bestHands = new ArrayList<>();
        for (Player player:players) {
            List<Card> playerAndTableCards = new ArrayList(cardsOnTable);
            playerAndTableCards.add(player.getCards()[0]);
            playerAndTableCards.add(player.getCards()[1]);
            Checker checker = new Checker(playerAndTableCards);
            String playerResult = checker.returnHand();//returns code for the hand ("BA" etc)
            bestHands.add(new BestHand(playerResult,player));

        }

        // Sort bestHands and determine the noOfWinners
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
        System.out.println(winningSum);


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

        System.out.println("#########-------ROUND ENDED--------#########");

        dealer.clearTableFromCards();

        for (Player player : players) {
            player.setStatus("Playing");
        }

        bettingRound = 1;
        roundStart();

    }

    public List<Player> getPlayers() {
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

    public List<Card> getCardsOnTable() {
        return cardsOnTable;
    }

    public int getLargestBet() {
        return largestBet;
    }

    public int getPot() {
        return pot;
    }

    public void addCardToTable(Card card) {
        this.cardsOnTable.add(card);
    }

    public int getBettingRound() {
        return bettingRound;
    }
}
