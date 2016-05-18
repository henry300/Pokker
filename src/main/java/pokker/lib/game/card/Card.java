package pokker.lib.game.card;

import com.google.gson.annotations.Expose;

/**
 * Represents a card
 */
public class Card implements Comparable<Card> {
    @Expose
    private final CardSuit suit;
    @Expose
    private final CardValue value;

    public Card(CardSuit suit, CardValue value) {
        this.suit = suit;
        this.value = value;
    }

    public CardSuit getSuit() {
        return suit;
    }

    public CardValue getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.format("%s of %s", value, suit);
    }

    @Override
    public int compareTo(Card card) {
        int compareResult = value.compareTo(card.getValue());

        if (compareResult != 0) {
            return compareResult;
        }

        return suit.compareTo(card.getSuit());
    }
}
