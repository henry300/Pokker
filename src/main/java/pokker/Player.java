package pokker;

import java.util.Scanner;

public class Player {
    private final Card[] cards = new Card[2];
    private int money;
    private String status;
    private int hasReacted;
    private int streetBet;  // How much the player has placeBet on this street.
    private final String name;
    private String[] allowedCheckActions = {"Fold", "Raise", "Call"};
    private String[] allowedCallActions = {"Fold", "Bet", "Check"};

    public Player(String name) {
        this.name = name;
    }

    /**
     * Sets player's cards
     *
     * @param cards
     */
    void setCards(Card[] cards) {
        this.cards[0] = cards[0];
        this.cards[1] = cards[1];
    }

    /**
     * Returns player's cards
     *
     * @return
     */
    Card[] getCards() {
        return new Card[]{cards[0], cards[1]};
    }

    /**
     * Sets player's street bet
     *
     * @param bet
     */
    void setStreetBet(int bet) {
        if (bet > streetBet) {
            money -= bet - streetBet;
            streetBet = bet;
        }
    }

    int act(int largestBet) {

        // Assign correct allowedActions for the player
        String[] allowedActions;
        if (streetBet < largestBet) {
            allowedActions = allowedCallActions;
        } else {
            allowedActions = allowedCheckActions;
        }

        Scanner scanner = new Scanner(System.in);

        // Provide info to the player about his/her allowed Actions
        System.out.println("Sinu kord, " + name);
        System.out.println("Sa oled juba see street mängu pannud " + streetBet + "€. Vabaraha alles: " + money + "€");
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

        setStreetBet(bet);
        return bet;
    }

    double getStreetBet() {
        return streetBet;
    }

    void resetStreetBet() {
        streetBet = 0;
    }
}
