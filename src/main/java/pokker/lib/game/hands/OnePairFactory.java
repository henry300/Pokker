package pokker.lib.game.hands;

import pokker.lib.game.card.Card;
import pokker.lib.game.card.CardListUtility;
import pokker.lib.game.card.CardValue;

import java.util.List;
import java.util.Map;

public class OnePairFactory implements HandFactory {
    @Override
    public FullHand createHand(List<Card> cards) {
        CardValue pair = null;
        Map<CardValue, List<Card>> groupedCards = CardListUtility.groupCardsByValue(cards);

        for (CardValue cardValue : groupedCards.keySet()) {
            List<Card> cardList = groupedCards.get(cardValue);

            if (cardList.size() == 1) {
                groupedCards.remove(cardValue);
                pair = cardValue;
                break;
            }
        }

        if (pair != null) {
            List<CardValue> comparableCards = CardListUtility.flattenGroups(groupedCards, Card::getValue);
            comparableCards.subList(0, 4);
            comparableCards.add(0, pair);

            return new FullHand(comparableCards, HandType.ONEPAIR);
        }
        return null;
    }
}
