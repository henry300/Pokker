package pokker.lib.game.hands;

import pokker.lib.game.card.Card;
import pokker.lib.game.card.CardValue;

import java.util.ArrayList;
import java.util.List;

public class StraightFactory implements HandFactory {
    @Override
    public Hand createHand(List<Card> cards) {
        List<CardValue> comparableValues = createComparableCardValues(cards);

        if (comparableValues != null) {
            return new Hand(comparableValues, HandType.STRAIGHT);
        }

        return null;
    }

    List<CardValue> createComparableCardValues(List<Card> cards) {
        int cardsAmnt = cards.size();
        List<CardValue> tempStraight = new ArrayList<>();
        boolean hasAce = false; // used to check for a wheel (https://en.wikipedia.org/wiki/Glossary_of_poker_terms#wheel)

        for (int i = 1; i < cardsAmnt; i++) {
            Card currentCard = cards.get(i);
            Card previousCard = cards.get(i - 1);

            if (previousCard.getValue() == CardValue.ACE) {
                hasAce = true;
            }

            if (isCardBeforeOf(previousCard, currentCard)) {
                tempStraight.add(previousCard.getValue());
                if (tempStraight.size() == 4) {
                    tempStraight.add(currentCard.getValue());
                    return tempStraight;
                }
            } else if (hasAce && currentCard.getValue() == CardValue.FIVE) {
                // https://en.wikipedia.org/wiki/Glossary_of_poker_terms#wheel
                tempStraight.add(CardValue.ACE);
            } else if (cardsAmnt - i < 5) {
                return null;
            } else {
                tempStraight.clear();
            }
        }
        return null;
    }

    private boolean isCardBeforeOf(Card precedingCard, Card succeedingCard) {
        return precedingCard.getValue().ordinal() + 1 == succeedingCard.getValue().ordinal();
    }

}
