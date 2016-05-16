package pokker.lib.game.hands;

public enum HandType {
    STRAIGHTFLUSH(new StraightFlushFactory()),
    FOUROFAKIND(new FourOfAKindFactory()),
    FULLHOUSE(new FullHouseFactory()),
    FLUSH(new FlushFactory()),
    STRAIGHT(new StraightFactory()),
    THREEOFAKIND(new ThreeOfAKindFactory()),
    TWOPAIR(new TwoPairFactory()),
    ONEPAIR(new OnePairFactory()),
    HIGHCARD(new HighCardFactory());

    private final HandFactory handFactory;

    HandType(HandFactory handFactory) {
        this.handFactory = handFactory;
    }

    public HandFactory getHandFactory() {
        return handFactory;
    }
}
