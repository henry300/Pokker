package pokker;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Dealer {
    Table table;

    Dealer(Table table) {
        this.table = table;
    }

    void shuffleDeck() {
        table.getDeck().shuffle();
    }

    void drawCardsToPlayers() {
        for (Player player : table.getPlayers()) {
            player.setCards(new Card[]{table.getDeck().draw(), table.getDeck().draw()});
        }
    }

    int askPlayerToAct(Player player) {

        // If player is AI, then always call/check. For console only!
        if (!player.isReal()) {
            System.out.println("It's player " + player.getName() + " turn (Current bet: " + player.getStreetBet() +"€ and money left "+ player.getMoney() + "€)");
            if (player.getStreetBet() < table.getLargestBet()) {
                System.out.println("He chose to call.");
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
        System.out.println("Your turn, " + player.getName());
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
        int bet; // Number to indicate how money flows. Possibly change variable name to something better.
        switch (allowedActions[decision]) {
            case "Fold":
            case "Check":
                bet = 0;
                break;
            case "Raise":
            case "Bet":
                // Ask how much the player bets/raises
                System.out.println("How much would you like to " + allowedActions[decision]);
                bet = scanner.nextInt();
                while (!(decision < 4) || !(decision > 0)) {
                    System.out.println("You have entered something wrong!");
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

}
