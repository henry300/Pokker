package pokker.lib.game.hands;

import pokker.lib.game.card.Card;
import pokker.lib.game.card.CardListUtility;
import pokker.lib.game.card.CardValue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FullHouseFactory implements HandFactory {
    @Override
    public FullHand createHand(List<Card> cards) {
        Map<CardValue, List<Card>> groupedValues = CardListUtility.groupCardsByValue(cards);
        CardValue threeOfAKind = null;
        CardValue pair = null;
        for (CardValue cardValue : groupedValues.keySet()) {
            List<Card> cardList = groupedValues.get(cardValue);
            if (cardList.size() == 3) {
                threeOfAKind = cardValue;
            } else if (cardList.size() == 2) {
                pair = cardValue;
            }
        }

        if (threeOfAKind != null && pair != null) {
            List<CardValue> comparableCards = new ArrayList<>();
            comparableCards.add(threeOfAKind);
            comparableCards.add(pair);
            return new FullHand(comparableCards, HandType.FULLHOUSE);
        }
        return null;
    }
}
