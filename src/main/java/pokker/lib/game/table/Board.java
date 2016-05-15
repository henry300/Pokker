package pokker.lib.game.table;

import pokker.lib.game.card.Card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a board of cards (on a table)
 */
public class Board {
    private final List<Card> boardCards = new ArrayList<>();

    public void add(Card card) {
        boardCards.add(card);
    }

    public List<Card> getCards() {
        return Collections.unmodifiableList(boardCards);
    }

    public void clear() {
        boardCards.clear();
    }
}
