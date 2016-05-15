package pokker.lib.game.hands;

import pokker.lib.game.card.Card;
import pokker.lib.game.card.CardListUtility;

import java.util.List;

public class HighCardFactory implements HandFactory {
    @Override
    public Hand createHand(List<Card> cards) {
        return new Hand(CardListUtility.extract(cards.subList(0, 5), Card::getValue), HandType.HIGHCARD);
    }
}
