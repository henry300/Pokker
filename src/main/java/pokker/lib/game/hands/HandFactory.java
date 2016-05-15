package pokker.lib.game.hands;

import pokker.lib.game.card.Card;

import java.util.List;

public interface HandFactory {
    /**
     * Creates a Hand if possible. If this factory cannot create a hand from this list of cards, returns null
     *
     * @see Hand
     * @param cards
     * @return Hand or null, if hand cannot be created
     */
    public Hand createHand(List<Card> cards);
}
