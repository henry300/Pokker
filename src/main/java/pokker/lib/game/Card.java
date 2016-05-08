package pokker.lib.game;

/**
 * Represents a card
 */
public class Card {
    private final CardSuit suit;
    private final CardValue value;

    Card(CardSuit suit, CardValue value) {
        this.suit = suit;
        this.value = value;
    }

    CardSuit getSuit() {
        return suit;
    }

    CardValue getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.format("%s of %s", value, suit);
    }
}
