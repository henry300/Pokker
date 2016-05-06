package pokker.lib;

/**
 * Represents betting rounds that happen during a poker game.
 */
public enum BettingRound {
    PREFLOP,
    FLOP,
    TURN,
    RIVER;

    public BettingRound next() {
        return values()[ordinal() + 1];
    }
}
