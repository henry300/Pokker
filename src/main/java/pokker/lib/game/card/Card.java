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
    private String cardStyle;

    public Card(CardSuit suit, CardValue value) {
        this.suit = suit;
        this.value = value;
        setCardStyle();
    }


    public String getCardStyle() {
        if (cardStyle == null) {
            setCardStyle();
        }
        return cardStyle;
    }

    public void setCardStyle() {
        String path = value.toString().toLowerCase() + "_of_" + suit.toString().toLowerCase() + ".png";
        cardStyle = "-fx-background-image: url(images/cards/" + path + ")";
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
