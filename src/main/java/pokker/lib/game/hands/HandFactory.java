package pokker.lib.game.hands;

import pokker.lib.game.card.Card;

import java.util.List;

public interface HandFactory {
    /**
     * Creates a FullHand if possible. If this factory cannot create a hand from this list of cards, returns null
     *
     * @param cards
     * @return FullHand or null, if hand cannot be created
     */
    public FullHand createHand(List<Card> cards);
}
