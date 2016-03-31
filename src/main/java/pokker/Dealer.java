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
        if (!player.getStatus().equals("Playing")) {
            return 0;
        }

        // If player is AI, then always call/check. For console only!
        if (!player.isReal()) {
            System.out.println("It's player " + player.getName() + " turn (Current bet: " + player.getStreetBet() +"€ and money left "+ player.getMoney() + "€)");
            if (player.getStreetBet() < table.getLargestBet()) {
                System.out.println("He chose to call.");
                player.setStreetBet(table.getLargestBet());
                return table.getLargestBet();
            } else {
                System.out.println("He chose to check.");
                return 0;
            }
        }

        int largestBet = table.getLargestBet();

        // Assign correct allowedActions for the player
        String[] allowedActions;
        if (player.getStreetBet() < largestBet) {
            allowedActions = player.getAllowedCallActions();
        } else {
            allowedActions = player.getAllowedCheckActions();
        }
        Scanner scanner = new Scanner(System.in);

        // Provide info to the player about his/her allowed Actions
        System.out.println("Your turn, " + player.getName() + " ("+ player.getCards()[0].suit + " " + player.getCards()[0].value + " || "+ player.getCards()[1].suit + " " + player.getCards()[1].value +")");
        System.out.println("You have already bet " + player.getStreetBet() + "€ in this street. Usable money left: " + player.getMoney() + "€");
        System.out.println("Largest bet is " + table.getLargestBet() + "€ right now.");
        System.out.println("You have the following choices:    (Type the right number to select)\n1) " + allowedActions[0] + "\n2) " + allowedActions[1] + "\n3) " + allowedActions[2]);

        // Ask player's decision
        int decision = scanner.nextInt() - 1;
        while (!(decision < 3) || !(decision >= 0)) {
            System.out.println("You have entered something wrong!");
            decision = scanner.nextInt() - 1;
        }
        System.out.println("Your action: " + allowedActions[decision]);

        // Act accordingly
        int bet = 0; // Number to indicate how money flows. Possibly change variable name to something better.
        switch (allowedActions[decision]) {
            case "Fold":
                player.setStatus("Folded");
                break;
            case "Check":
                bet = 0;
                break;
            case "Raise":
            case "Bet":
                // Ask how much the player bets/raises
                System.out.println("How much would you like to " + allowedActions[decision]);
                bet = scanner.nextInt();
                while (!(bet < player.getStreetBet() + player.getMoney())) {
                    System.out.println("You can't enter this large amount");
                    bet = scanner.nextInt();
                }
                break;
            case "Call":
                bet = largestBet;
                break;
            default:
                bet = 0;
                break;
        }
        player.setStreetBet(bet);

        return bet;
    }

    void clearTableFromCards() {
        table.setCardsOnTable(new ArrayList<Card>());
    }
}
