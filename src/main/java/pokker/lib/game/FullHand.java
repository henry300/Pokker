package pokker.lib.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a full hand - consisting of the cards on the board as well as the cards in the player's hand
 */
public class FullHand implements Comparable<FullHand> {
    /**
     * Hand of 2 cards. Treated as unmutable.
     */
    private final Hand hand;

    /**
     * Board of cards. Treated as unmutable.
     */
    private final Board board;

    /**
     * List of all cards (cards on the board as well as in the hand)
     */
    private final List<Card> allCards;

    /**
     * Type of this full hand
     */
    private final HandType handType;

    FullHand(Hand hand, Board board) {
        this.hand = hand;
        this.board = board;

        List<Card> allCards = new ArrayList<>(board.getCards());
        allCards.addAll(hand.getCards());

        this.allCards = Collections.unmodifiableList(allCards);
        this.handType = getInitialHandtype();
    }

    private HandType getInitialHandtype() {
        // Not the best type of implementation, since some pairs/three of a kinds might get checked 2-3 times.
        // TODO: fixme
        for (HandType handType : HandType.values()) {
            if (handType.isFullhand(this)) {
                return handType;
            }
        }

        return HandType.HIGHCARD;
    }

    List<Card> getAllCards() {
        return allCards;
    }

    HandType getHandType() {
        return handType;
    }

    Hand getHand() {
        return hand;
    }

    @Override
    public int compareTo(FullHand fullHand) {
        int compareResult = handType.compareTo(fullHand.getHandType());
        if (compareResult == 0) {
            return hand.compareTo(fullHand.getHand());
        } else {
            return compareResult;
        }
    }
}
