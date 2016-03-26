package pokker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Checker {
    List<Card> allCards;

    public boolean isFlush() {
        int hearts = 0;
        int spades = 0;
        int diamonds = 0;
        int clubs = 0;
        for (Card card : allCards) {
            if (card.suit == CardSuit.HEARTS) {
                hearts += 1;
            } else if (card.suit == CardSuit.CLUBS) {
                clubs += 1;
            } else if (card.suit == CardSuit.DIAMONDS) {
                diamonds += 1;
            } else {
                spades += 1;
            }
        }
        return diamonds > 4 || clubs > 4 || spades > 4 || hearts > 4;
    }

    public boolean isStraight() {
        List<CardValue> valueList = new ArrayList<>();
        for (Card card : allCards) {
            valueList.add(card.value);
        }
        if (valueList.contains(CardValue.ACE) && valueList.contains(CardValue.TWO) && valueList.contains(CardValue.THREE) && valueList.contains(CardValue.FOUR) && valueList.contains(CardValue.FIVE)) {
            return true;
        } else if (valueList.contains(CardValue.SIX) && valueList.contains(CardValue.TWO) && valueList.contains(CardValue.THREE) && valueList.contains(CardValue.FOUR) && valueList.contains(CardValue.FIVE)) {
            return true;
        } else if (valueList.contains(CardValue.SIX) && valueList.contains(CardValue.SEVEN) && valueList.contains(CardValue.THREE) && valueList.contains(CardValue.FOUR) && valueList.contains(CardValue.FIVE)) {
            return true;
        } else if (valueList.contains(CardValue.SIX) && valueList.contains(CardValue.SEVEN) && valueList.contains(CardValue.EIGHT) && valueList.contains(CardValue.FOUR) && valueList.contains(CardValue.FIVE)) {
            return true;
        } else if (valueList.contains(CardValue.SIX) && valueList.contains(CardValue.SEVEN) && valueList.contains(CardValue.EIGHT) && valueList.contains(CardValue.NINE) && valueList.contains(CardValue.FIVE)) {
            return true;
        } else if (valueList.contains(CardValue.SIX) && valueList.contains(CardValue.SEVEN) && valueList.contains(CardValue.EIGHT) && valueList.contains(CardValue.NINE) && valueList.contains(CardValue.TEN)) {
            return true;
        } else if (valueList.contains(CardValue.JACK) && valueList.contains(CardValue.SEVEN) && valueList.contains(CardValue.EIGHT) && valueList.contains(CardValue.NINE) && valueList.contains(CardValue.TEN)) {
            return true;
        } else if (valueList.contains(CardValue.JACK) && valueList.contains(CardValue.QUEEN) && valueList.contains(CardValue.EIGHT) && valueList.contains(CardValue.NINE) && valueList.contains(CardValue.TEN)) {
            return true;
        } else if (valueList.contains(CardValue.JACK) && valueList.contains(CardValue.QUEEN) && valueList.contains(CardValue.KING) && valueList.contains(CardValue.NINE) && valueList.contains(CardValue.TEN)) {
            return true;
        } else if (valueList.contains(CardValue.JACK) && valueList.contains(CardValue.QUEEN) && valueList.contains(CardValue.KING) && valueList.contains(CardValue.ACE) && valueList.contains(CardValue.TEN)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isFour() {
        List<CardValue> valueList = new ArrayList<>();
        for (Card card : allCards) {
            valueList.add(card.value);
        }
        if (Collections.frequency(valueList, CardValue.ACE) > 3) {
            return true;
        } else if (Collections.frequency(valueList, CardValue.KING) > 3) {
            return true;
        } else if (Collections.frequency(valueList, CardValue.QUEEN) > 3) {
            return true;
        } else if (Collections.frequency(valueList, CardValue.JACK) > 3) {
            return true;
        } else if (Collections.frequency(valueList, CardValue.TEN) > 3) {
            return true;
        } else if (Collections.frequency(valueList, CardValue.NINE) > 3) {
            return true;
        } else if (Collections.frequency(valueList, CardValue.EIGHT) > 3) {
            return true;
        } else if (Collections.frequency(valueList, CardValue.SEVEN) > 3) {
            return true;
        } else if (Collections.frequency(valueList, CardValue.SIX) > 3) {
            return true;
        } else if (Collections.frequency(valueList, CardValue.FIVE) > 3) {
            return true;
        } else if (Collections.frequency(valueList, CardValue.FOUR) > 3) {
            return true;
        } else if (Collections.frequency(valueList, CardValue.THREE) > 3) {
            return true;
        } else if (Collections.frequency(valueList, CardValue.TWO) > 3) {
            return true;
        } else {
            return false;
        }
    }

}



