package pokker.client;

import pokker.lib.Player;

import java.util.ArrayList;

public class Dealer {
    private Table table;

    Dealer(Table table) {
        this.table = table;
    }

    void shuffleDeck() {
        table.getDeck().shuffle();
    }

    void drawCardsToPlayers() {
        // Needs to be redone, because, in real life each player will first...
        // ...get one card and then second, not both at the same time
        for (Player player : table.getPlayers()) {
            player.setCards(new Card[]{table.getDeck().draw(), table.getDeck().draw()});
        }
    }

    public void dealNextCards() {
        switch (table.getBettingRound()) {
            case FLOP:
                dealFlop();
                break;
            case TURN:
                dealTurn();
                break;
            case RIVER:
                dealRiver();
                break;
        }
    }

    private void dealFlop() {
        table.addCardToTable(table.getDeck().draw());
        table.addCardToTable(table.getDeck().draw());
        table.addCardToTable(table.getDeck().draw());
    }

    private void dealTurn() {
        table.addCardToTable(table.getDeck().draw());
    }

    private void dealRiver() {
        table.addCardToTable(table.getDeck().draw());
    }

    int askPlayerToAct(Player player) {
        System.out.println("It's player " + player.getName() + " turn (Current bet: " + player.getStreetBet() + "€ and money left " + player.getMoney() + "€)");
        int bet = player.act(table.getLargestBet());
        player.setStreetBet(bet);

        return bet;
    }

    void clearTableFromCards() {
        table.setCardsOnTable(new ArrayList<Card>());
    }
}
