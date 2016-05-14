package pokker.lib.game.player;

import pokker.lib.game.card.Card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a hand of 2 cards (in the player's hand)
 */
public class Hand implements Comparable<Hand> {
    private final List<Card> cards = new ArrayList<>();

    public List<Card> getCards() {
        return Collections.unmodifiableList(cards);
    }

    void add(Card card) {
        cards.add(card);
    }

    void clear() {
        cards.clear();
    }

    @Override
    public int compareTo(Hand hand) {
        if (cards.size() != hand.getCards().size()) {
            throw new RuntimeException("Hands are not of the same size!");
        }
        for (int i = 0; i < cards.size(); i++) {
            int compareResult = cards.get(i).compareTo(hand.getCards().get(i));

            if (compareResult != 0) {
                return compareResult;
            }
        }

        return 0;
    }

    @Override
    public String toString() {
        if (cards.isEmpty()) {
            return "Empty hand!";
        }

        StringBuilder builder = new StringBuilder("(").append(cards.get(0));

        for (int i = 1; i < cards.size(); i++) {
            builder.append("||").append(cards.get(i));
        }

        builder.append(")");

        return builder.toString();
    }
}
