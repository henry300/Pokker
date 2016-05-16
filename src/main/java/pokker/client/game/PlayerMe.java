package pokker.client.game;

import pokker.lib.game.player.Player;
import pokker.lib.game.table.Action;

import java.util.Scanner;

/**
 * Represents the "Me"-player. An object of this class should be created for every table that the player is playing on.
 */
public class PlayerMe extends Player {
    private final Action[] allowedCallActions = {Action.FOLD, Action.RAISE, Action.CALL};
    private final Action[] allowedCheckActions = {Action.FOLD, Action.BET, Action.CHECK};

    public PlayerMe(String name) {
        super(name);
    }

    @Override
    public int act(int largestBet) {
        // Assign correct allowedActions for the player
        Action[] allowedActions;
        if (getStreetBet() < largestBet) {
            allowedActions = allowedCallActions;
        } else {
            allowedActions = allowedCheckActions;
        }

        Scanner scanner = new Scanner(System.in);
        // Provide info to the player about his/her allowed Action
        System.out.printf("Your turn, %s, %s%n", getName(), getHand().toString());
        System.out.printf("You have already bet %d in this street. Money left: %d%n", getStreetBet(), getMoney());
        System.out.printf("Largest bet is %d€ right now.%n", largestBet);

        System.out.println("You have the following choices: (Type the right number to select)");
        for (int i = 0; i < allowedActions.length; i++) {
            System.out.printf("%d) %s%n", i, allowedActions[i].toString());
        }

        // Ask player's decision
        int decision = scanner.nextInt() - 1;
        while (decision > allowedActions.length || decision <= 0) {
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
                while (bet > getStreetBet() + getMoney()) {
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
