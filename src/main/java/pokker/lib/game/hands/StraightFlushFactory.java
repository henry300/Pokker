package pokker.lib.game.hands;

import pokker.lib.game.card.Card;
import pokker.lib.game.card.CardListUtility;
import pokker.lib.game.card.CardSuit;
import pokker.lib.game.card.CardValue;

import java.util.List;
import java.util.Map;

public class StraightFlushFactory implements HandFactory {
    @Override
    public FullHand createHand(List<Card> cards) {
        List<CardValue> comparableCardValues = createComparableCardValues(cards);
        if (comparableCardValues != null) {
            return new FullHand(comparableCardValues, HandType.STRAIGHTFLUSH);
        }

        return null;
    }

    private List<CardValue> createComparableCardValues(List<Card> cards) {
        Map<CardSuit, List<Card>> cardsGroupedBySuit = CardListUtility.groupCardsBySuit(cards);
        StraightFactory straightFactory = new StraightFactory();
        for (List<Card> suitGroup : cardsGroupedBySuit.values()) {
            if (suitGroup.size() >= 5) {
                List<CardValue> comparableValues = straightFactory.createComparableCardValues(suitGroup);

                if (comparableValues != null) {
                    return comparableValues;
                }
            }
        }

        return null;
    }

}
