package pokker;

public enum Round {
    PREFLOP,
    FLOP,
    TURN,
    RIVER;

    public Round next(){
        return values()[ordinal() + 1];
    }
}
