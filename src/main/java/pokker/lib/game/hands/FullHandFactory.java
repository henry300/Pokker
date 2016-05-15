package pokker.lib.game.hands;

import pokker.lib.game.card.Card;
import pokker.lib.game.player.PlayerHand;
import pokker.lib.game.table.Board;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Creates a hand with the correct handtype.
 *
 * @see HandType
 * @see Hand
 */
public class FullHandFactory implements HandFactory {
    private final List<HandFactory> factories;

    public FullHandFactory() {
        factories = loadFactories();
    }

    /**
     * Loads factories from the HandType enum.
     *
     * @return
     * @see HandType
     */
    private List<HandFactory> loadFactories() {
        List<HandFactory> factories = new ArrayList<>();

        for (HandType handType : HandType.values()) {
            factories.add(handType.getHandFactory());
        }

        return factories;
    }

    @Override
    public Hand createHand(List<Card> cards) {
        for (HandFactory factory : factories) {
            Hand hand = factory.createHand(cards);

            if (hand != null) {
                return hand;
            }
        }

        return null;
    }

    public Hand createHand(PlayerHand hand, Board board) {
        List<Card> cards = new ArrayList<>();

        cards.addAll(hand.getCards());
        cards.addAll(board.getCards());

        Collections.sort(cards);

        return createHand(cards);
    }
}
