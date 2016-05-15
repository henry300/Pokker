package pokker.lib.game.card;

/**
 * Represents a card
 */
public class Card implements Comparable<Card> {
    private final CardSuit suit;
    private final CardValue value;
    private String pictureName;

    public Card(CardSuit suit, CardValue value) {
        this.suit = suit;
        this.value = value;
        setPictureName();
    }


    public String getPictureName() {
        return pictureName;
    }

    public void setPictureName() {
        pictureName = value.toString().toLowerCase() + "_of_" + suit.toString().toLowerCase() + ".png";
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
