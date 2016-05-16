package pokker.lib.game.hands;

import pokker.lib.game.card.Card;

import java.util.List;

public interface HandFactory {
    /**
     * Creates a Hand if possible. If this factory cannot create a hand from this list of cards, returns null
     *
     * @param cards
     * @return Hand or null, if hand cannot be created
     * @see Hand
     */
    public Hand createHand(List<Card> cards);
}
