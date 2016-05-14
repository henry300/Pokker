package pokker.lib.game.card;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Provides utility functions to work with lists of cards
 */
public final class CardListUtility {
    private CardListUtility() {

    }

    public static <T> List<T> extract(List<Card> cards, Function<? super Card, ? extends T> f) {
        return cards.stream().map(f).collect(Collectors.toList());
    }

    public static Map<CardValue, List<Card>> groupCardsByValue(List<Card> cards) {
        return cards.stream().collect(Collectors.groupingBy(Card::getValue));
    }

    public static Map<CardSuit, List<Card>> groupCardsBySuit(List<Card> cards) {
        return cards.stream().collect(Collectors.groupingBy(Card::getSuit));
    }

    public static <T, K> List<T> flattenGroups(Map<K, List<Card>> groupedCards, Function<? super Card, ? extends T> f) {
        List<T> result = new ArrayList<>();
        for (List<Card> cardList : groupedCards.values()) {
            for (Card card : cardList) {
                result.add(f.apply(card));
            }
        }

        return result;
    }

}
