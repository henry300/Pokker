package pokker.lib.game.card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a deck of cards
 */
public class Deck {
    private final List<Card> cards;

    /**
     * The card index that we are at. Using an index is a simple alternative to re-creating a queue every time we need
     * to shuffle the cards. This way, we "create" the deck of cards only once.
     */
    private int atCard = 0;

    /**
     * Creates a deck of cards, ordered by suit and value.
     */
    public Deck() {
        cards = new ArrayList<>();

        for (CardSuit suit : CardSuit.values()) {
            for (CardValue value : CardValue.values()) {
                cards.add(new Card(suit, value));
            }
        }
    }

    /**
     * Shuffles and resets(!) the deck.
     **/
    public void shuffle() {
        atCard = 0;
        Collections.shuffle(cards);
    }

    /**
     * Draws the "top" card from the deck. If deck is empty, returns null
     *
     * @return Top card or null, if deck is empty
     */
    public Card draw() {
        if (!isEmpty()) {
            return cards.get(atCard++);
        }

        return null;
    }

    /**
     * Tells whether the deck is empty or not
     *
     * @return True if deck is empty. False if not
     */
    boolean isEmpty() {
        return atCard >= cards.size();
    }
}
