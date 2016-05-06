package pokker.lib;

/**
 * Represents betting rounds that happen during a poker game.
 */
public enum BettingRound {
    RIVER(null, 1),
    TURN(RIVER, 1),
    FLOP(TURN, 3),
    PREFLOP(FLOP, 0);

    /**
     * Holds a reference to the succeeding betting round. If there is no succeeding betting round, this field should
     * be set to NULL.
     */
    private final BettingRound nextBettingRound;

    /**
     * How many cards should be dealt on this betting round
     */
    private final int cardsToBeDealt;

    BettingRound(BettingRound nextBettingRound, int cardsToBeDealt) {
        this.nextBettingRound = nextBettingRound;
        this.cardsToBeDealt = cardsToBeDealt;
    }

    public int getAmountOfCardsToDeal() {
        return cardsToBeDealt;
    }

    public BettingRound next() throws NoNextBettingRoundException {
        if (nextBettingRound == null) {
            throw new NoNextBettingRoundException();
        }

        return nextBettingRound;
    }
}
