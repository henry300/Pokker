package pokker.lib.game.hands;

import pokker.lib.game.card.Card;
import pokker.lib.game.card.CardListUtility;
import pokker.lib.game.card.CardSuit;

import java.util.List;
import java.util.Map;

public class FlushFactory implements HandFactory {
    @Override
    public Hand createHand(List<Card> cards) {
        Map<CardSuit, List<Card>> groupedSuits = CardListUtility.groupCardsBySuit(cards);

        for (List<Card> cardList : groupedSuits.values()) {
            if (cardList.size() >= 5) {
                return new Hand(CardListUtility.extract(cardList.subList(0, 5), Card::getValue), HandType.FLUSH);
            }
        }

        return null;
    }
}
