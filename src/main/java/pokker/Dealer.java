package pokker;

import java.util.ArrayList;
import java.util.Scanner;

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
        if (table.getBettingRound() == 2) {
            dealFlop();
        } else if (table.getBettingRound() == 3) {
            dealTurn();
        } else if (table.getBettingRound() == 4) {
            dealRiver();
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

    private void  dealRiver() {
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
