package pokker.client.game;

import pokker.lib.game.player.Player;
import pokker.lib.game.table.Action;

/**
 * Represents the "Me"-player. An object of this class should be created for every table that the player is playing on.
 */
public class PlayerMe extends Player {
    private final Action[] allowedCallActions = {Action.FOLD, Action.RAISE, Action.CALL};
    private final Action[] allowedCheckActions = {Action.FOLD, Action.BET, Action.CHECK};

    public PlayerMe(String name) {
        super(name);
    }

    public Action[] getAllowedActions(int largestBet) {
        Action[] allowedActions;

        if (getStreetBet() < largestBet) {
            allowedActions = allowedCallActions;
        } else {
            allowedActions = allowedCheckActions;
        }

        return allowedActions;
    }
}
