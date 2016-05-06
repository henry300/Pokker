package pokker.lib;

/**
 * Represents betting rounds that happen during a poker game.
 */
public enum BettingRound {
    RIVER(null),
    TURN(RIVER),
    FLOP(TURN),
    PREFLOP(FLOP);

    private final BettingRound nextBettingRound;

    BettingRound(BettingRound nextBettingRound) {
        this.nextBettingRound = nextBettingRound;
    }

    public BettingRound next() throws NoNextBettingRoundException {
        if (nextBettingRound == null) {
            throw new NoNextBettingRoundException();
        }

        return nextBettingRound;
    }
}
