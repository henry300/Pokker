package pokker.lib.game.hands;

import pokker.lib.game.card.Card;
import pokker.lib.game.card.CardListUtility;
import pokker.lib.game.card.CardValue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FourOfAKindFactory implements HandFactory {
    @Override
    public FullHand createHand(List<Card> cards) {
        Map<CardValue, List<Card>> groupValues = CardListUtility.groupCardsByValue(cards);
        CardValue fourOfAKindValue = null;
        CardValue kickerValue = CardValue.TWO;

        for (CardValue cardValue : groupValues.keySet()) {
            List<Card> cardList = groupValues.get(cardValue);
            if (cardList.size() == 4) {
                fourOfAKindValue = cardValue;
            } else if (cardValue.compareTo(kickerValue) == 1) {
                kickerValue = cardValue;
            }
        }

        if (fourOfAKindValue != null) {
            List<CardValue> comparableCardValues = new ArrayList<>();

            comparableCardValues.add(fourOfAKindValue);
            comparableCardValues.add(kickerValue);

            return new FullHand(comparableCardValues, HandType.FOUROFAKIND);
        }

        return null;
    }
}
