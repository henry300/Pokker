package pokker.lib.game.hands;

import pokker.lib.game.card.Card;
import pokker.lib.game.card.CardListUtility;
import pokker.lib.game.card.CardValue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TwoPairFactory implements HandFactory {
    @Override
    public FullHand createHand(List<Card> cards) {
        Map<CardValue, List<Card>> groupedValues = CardListUtility.groupCardsByValue(cards);
        List<CardValue> comparableCards = new ArrayList<>();

        for (CardValue cardValue : groupedValues.keySet()) {
            List<Card> cardList = groupedValues.get(cardValue);

            if (cardList.size() == 2) {
                groupedValues.remove(cardValue);
                comparableCards.add(cardValue);

                if (comparableCards.size() >= 2) {
                    break;
                }
            }
        }

        if (comparableCards.size() == 2) {
            comparableCards.addAll(CardListUtility.flattenGroups(groupedValues, Card::getValue));

            return new FullHand(comparableCards, HandType.TWOPAIR);
        }

        return null;
    }
}
