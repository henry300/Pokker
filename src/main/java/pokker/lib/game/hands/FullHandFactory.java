package pokker.lib.game.hands;

import pokker.lib.game.card.Card;
import pokker.lib.game.player.Hand;
import pokker.lib.game.table.Board;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FullHandFactory implements HandFactory {
    private final List<HandFactory> factories;

    public FullHandFactory() {
        factories = loadFactories();
    }

    private List<HandFactory> loadFactories() {
        List<HandFactory> factories = new ArrayList<>();

        for (HandType handType : HandType.values()) {
            factories.add(handType.getHandFactory());
        }

        return factories;
    }

    @Override
    public FullHand createHand(List<Card> cards) {
        for (HandFactory factory : factories) {
            FullHand fullHand = factory.createHand(cards);

            if(fullHand != null) {
                return fullHand;
            }
        }

        return null;
    }

    public FullHand createHand(Hand hand, Board board) {
        List<Card> cards = new ArrayList<>();

        cards.addAll(hand.getCards());
        cards.addAll(board.getCards());

        Collections.sort(cards);

        return createHand(cards);
    }
}
