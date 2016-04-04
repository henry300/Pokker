package pokker;

import java.util.*;

public class Deck {
    private final List<Card> cards;
    private int atCard = 0;

    Deck(List<Card> cards) {
        this.cards = cards;
    }

    Deck() {
        cards = new ArrayList<>();

        for (CardSuit suit : CardSuit.values()) {
            for (CardValue value : CardValue.values()) {
                cards.add(new Card(suit, value));
            }
        }
    }

    /**
     * Shuffles and resets(!) the deck.
     */
    void shuffle() {
        atCard = 0;
        Collections.shuffle(cards);
    }

    /**
     * Draws the "top" card from the deck. If deck is empty, returns null
     *
     * @return Top card or null, if deck is empty
     */
    Card draw() {
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
