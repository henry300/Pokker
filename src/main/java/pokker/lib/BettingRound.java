package pokker.lib;

public enum BettingRound {
    PREFLOP,
    FLOP,
    TURN,
    RIVER;

    public BettingRound next() {
        return values()[ordinal() + 1];
    }
}
