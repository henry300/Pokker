package pokker.lib.game.hands;

import pokker.lib.game.card.Card;
import pokker.lib.game.card.CardListUtility;
import pokker.lib.game.card.CardValue;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TwoPairFactory implements HandFactory {
    @Override
    public Hand createHand(List<Card> cards) {
        Map<CardValue, List<Card>> groupedValues = CardListUtility.groupCardsByValue(cards);
        CardValue pair1 = null;
        CardValue pair2 = null;

        for (CardValue cardValue : groupedValues.keySet()) {
            List<Card> cardList = groupedValues.get(cardValue);

            if (cardList.size() == 2) {
                if (pair1 == null) {
                    pair1 = cardValue;
                } else {
                    pair2 = cardValue;

                    if (pair2.compareTo(pair1) == 1) {
                        CardValue temp = pair1;
                        pair1 = pair2;
                        pair2 = temp;
                    }
                    return new Hand(createComparableCards(cards, pair1, pair2), HandType.TWOPAIR);
                }
            }
        }

        return null;
    }

    private List<CardValue> createComparableCards(List<Card> cards, CardValue pair1, CardValue pair2) {
        List<CardValue> comparableCards = cards.stream().
                filter(card -> card.getValue() != pair1 && card.getValue() != pair2).
                map(Card::getValue).collect(Collectors.toList());

        comparableCards.add(0, pair1);
        comparableCards.add(1, pair2);

        return comparableCards;
    }
}
