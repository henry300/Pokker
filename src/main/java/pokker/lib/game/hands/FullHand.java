package pokker.lib.game.hands;

import pokker.lib.game.card.CardValue;

import java.util.Collections;
import java.util.List;

/**
 * Represents a full hand - consisting of the cards on the board as well as the cards in the player's hand.
 */
public class FullHand implements Comparable<FullHand> {
    /**
     * Used to compare hands. A "higher" hand type wins and must not be compared any further.
     */
    private final HandType handType;
    /**
     * Used to compare hands that are of the same type. This list should be simplified as much as possible,
     * e.g: let's say we have a hand of 5,5,5,5,3,7,6. This is a four of a kind; the value of the four of a kind is 5
     * and the kicker is 7. So this hand can be reduced to a list of two values: 5 (value of the four of a kind) and 7
     * (value of the kicker). This list of 5,7 can now be compared very easily (by first comparing the first value and
     * if it is equal, then comparing the second value).
     */
    private final List<CardValue> comparableCardValues;

    FullHand(List<CardValue> comparableCardValues, HandType handType) {
        this.comparableCardValues = Collections.unmodifiableList(comparableCardValues);
        this.handType = handType;
    }

    private HandType getHandType() {
        return handType;
    }

    public List<CardValue> getComparableCardValues() {
        return comparableCardValues;
    }

    public int compareTo(FullHand otherHand) {
        int compareResult = handType.compareTo(otherHand.getHandType());

        if (compareResult != 0) {
            return compareResult;
        }

        return compareCardsTo(otherHand.getComparableCardValues());
    }

    private int compareCardsTo(List<CardValue> otherCardValues) {
        int compareResult;
        for (int i = 0; i < comparableCardValues.size(); i++) {
            CardValue cardValue1 = comparableCardValues.get(i);
            CardValue cardValue2 = otherCardValues.get(i);
            compareResult = cardValue1.compareTo(cardValue2);

            if (compareResult != 0) {
                return compareResult;
            }
        }

        return 0;
    }
}
