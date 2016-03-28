package pokker;

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
        System.out.println("Sinu kord, " + player.getName());
        System.out.println("Sa oled juba see street mängu pannud " + player.getStreetBet() + "€. Vabaraha alles: " + player.getMoney() + "€");
        System.out.println("Sul on järgnevad valikud:    (Valimiseks kirjuta vastav number)\n1) " + allowedActions[0] + "\n2) " + allowedActions[1] + "\n3) " + allowedActions[2]);

        // Ask player's decision
        int decision = scanner.nextInt() - 1;
        while (!(decision < 3) || !(decision >= 0)) {
            System.out.println("Sisestasid midagi valesti!");
            decision = scanner.nextInt() - 1;
        }
        System.out.println("Sinu toiming: " + allowedActions[decision]);

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
                System.out.println("Kui palju soovid " + allowedActions[decision]);
                bet = scanner.nextInt();
                while (!(decision < 4) || !(decision > 0)) {
                    System.out.println("Sisestasid midagi valesti!");
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
