package pokker.lib.game.hands;

import pokker.lib.game.card.Card;
import pokker.lib.game.card.CardListUtility;
import pokker.lib.game.card.CardValue;

import java.util.List;
import java.util.Map;

public class ThreeOfAKindFactory implements HandFactory {
    @Override
    public Hand createHand(List<Card> cards) {
        Map<CardValue, List<Card>> groupedValues = CardListUtility.groupCardsByValue(cards);

        CardValue threeOfAKindValue = null;
        for (CardValue cardValue : groupedValues.keySet()) {
            List<Card> cardList = groupedValues.get(cardValue);

            if (cardList.size() == 3) {
                threeOfAKindValue = cardValue;
                break;
            }
        }

        if (threeOfAKindValue != null) {
            groupedValues.remove(threeOfAKindValue);

            List<CardValue> comparableValues = CardListUtility.flattenGroups(groupedValues, Card::getValue);
            comparableValues.add(0, threeOfAKindValue);

            return new Hand(comparableValues, HandType.THREEOFAKIND);
        }
        return null;
    }
}
