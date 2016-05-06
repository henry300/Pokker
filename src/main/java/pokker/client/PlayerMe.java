package pokker.client;

import pokker.lib.Action;
import pokker.lib.Player;

import java.util.Scanner;

/**
 * Represents the "Me"-player. An object of this class should be created for every table that the player is playing on.
 */
public class PlayerMe extends Player {
    PlayerMe(String name) {
        super(name);
    }

    @Override
    public int act(int largestBet) {
        // Assign correct allowedActions for the player
        Action[] allowedActions;
        if (getStreetBet() < largestBet) {
            allowedActions = getAllowedCallActions();
        } else {
            allowedActions = getAllowedCheckActions();
        }

        Scanner scanner = new Scanner(System.in);
        // Provide info to the player about his/her allowed Action
        System.out.printf("Your turn, %s (%s || %s)", getName(), getCards()[0].toString(), getCards()[1].toString());
        System.out.println("You have already bet " + getStreetBet() + "€ in this street. Usable money left: " + getMoney() + "€");
        System.out.println("Largest bet is " + largestBet + "€ right now.");
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
            case FOLD:
                break;
            case CHECK:
                bet = 0;
                break;
            case RAISE:
            case BET:
                // Ask how much the player bets/raises
                System.out.println("How much would you like to " + allowedActions[decision]);
                bet = scanner.nextInt();
                while (!(bet < getStreetBet() + getMoney())) {
                    System.out.println("You can't enter this large amount");
                    bet = scanner.nextInt();
                }
                break;
            case CALL:
                bet = largestBet;
                break;
            default:
                bet = 0;
                break;
        }

        return bet;
    }
}
