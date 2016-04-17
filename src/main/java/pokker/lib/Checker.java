package pokker.lib;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Checker {
    List<Card> allCards;

    Checker(List<Card> cards) {
        this.allCards = cards;
    }

    public boolean isStraightFlush() {
        if (this.isFlush() && this.isStraight()) {
            return true;
        } else {
            return false;
        }
    }

    public Hand valueStraightFlush() {
        return new Hand(HandType.STRAIGHTFLUSH, this.valueOfStraightCardValue(), CardValue.NONE);
    }

    public CardValue valueOfStraightCardValue() {
        List<CardValue> valueList = new ArrayList<>();
        for (Card card : allCards) {
            valueList.add(card.getValue());
        }
        if (valueList.contains(CardValue.JACK) && valueList.contains(CardValue.QUEEN) && valueList.contains(CardValue.KING) && valueList.contains(CardValue.ACE) && valueList.contains(CardValue.TEN)) {
            return CardValue.ACE;
        } else if (valueList.contains(CardValue.JACK) && valueList.contains(CardValue.QUEEN) && valueList.contains(CardValue.KING) && valueList.contains(CardValue.NINE) && valueList.contains(CardValue.TEN)) {
            return CardValue.KING;
        } else if (valueList.contains(CardValue.JACK) && valueList.contains(CardValue.QUEEN) && valueList.contains(CardValue.EIGHT) && valueList.contains(CardValue.NINE) && valueList.contains(CardValue.TEN)) {
            return CardValue.QUEEN;
        } else if (valueList.contains(CardValue.JACK) && valueList.contains(CardValue.SEVEN) && valueList.contains(CardValue.EIGHT) && valueList.contains(CardValue.NINE) && valueList.contains(CardValue.TEN)) {
            return CardValue.JACK;
        } else if (valueList.contains(CardValue.SIX) && valueList.contains(CardValue.SEVEN) && valueList.contains(CardValue.EIGHT) && valueList.contains(CardValue.NINE) && valueList.contains(CardValue.TEN)) {
            return CardValue.TEN;
        } else if (valueList.contains(CardValue.SIX) && valueList.contains(CardValue.SEVEN) && valueList.contains(CardValue.EIGHT) && valueList.contains(CardValue.NINE) && valueList.contains(CardValue.FIVE)) {
            return CardValue.NINE;
        } else if (valueList.contains(CardValue.SIX) && valueList.contains(CardValue.SEVEN) && valueList.contains(CardValue.EIGHT) && valueList.contains(CardValue.FOUR) && valueList.contains(CardValue.FIVE)) {
            return CardValue.EIGHT;
        } else if (valueList.contains(CardValue.SIX) && valueList.contains(CardValue.SEVEN) && valueList.contains(CardValue.THREE) && valueList.contains(CardValue.FOUR) && valueList.contains(CardValue.FIVE)) {
            return CardValue.SEVEN;
        } else if (valueList.contains(CardValue.SIX) && valueList.contains(CardValue.TWO) && valueList.contains(CardValue.THREE) && valueList.contains(CardValue.FOUR) && valueList.contains(CardValue.FIVE)) {
            return CardValue.SIX;
        } else if (valueList.contains(CardValue.ACE) && valueList.contains(CardValue.TWO) && valueList.contains(CardValue.THREE) && valueList.contains(CardValue.FOUR) && valueList.contains(CardValue.FIVE)) {
            return CardValue.FIVE;
        } else {
            return null;
        }

    }


    public boolean isFlush() {
        int hearts = 0;
        int spades = 0;
        int diamonds = 0;
        int clubs = 0;
        for (Card card : allCards) {
            if (card.getSuit() == CardSuit.HEARTS) {
                hearts += 1;
            } else if (card.getSuit() == CardSuit.CLUBS) {
                clubs += 1;
            } else if (card.getSuit() == CardSuit.DIAMONDS) {
                diamonds += 1;
            } else {
                spades += 1;
            }
        }
        return diamonds > 4 || clubs > 4 || spades > 4 || hearts > 4;
    }

    public Hand valueFlush() {
        return new Hand(HandType.FLUSH, CardValue.NONE, CardValue.NONE);
    }


    public boolean isStraight() {
        List<CardValue> valueList = new ArrayList<>();
        for (Card card : allCards) {
            valueList.add(card.getValue());
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

    public Hand valueOfStraight() {
        List<CardValue> valueList = new ArrayList<>();
        for (Card card : allCards) {
            valueList.add(card.getValue());
        }
        if (valueList.contains(CardValue.JACK) && valueList.contains(CardValue.QUEEN) && valueList.contains(CardValue.KING) && valueList.contains(CardValue.ACE) && valueList.contains(CardValue.TEN)) {
            return new Hand(HandType.STRAIGHT, CardValue.ACE, CardValue.NONE);
        } else if (valueList.contains(CardValue.JACK) && valueList.contains(CardValue.QUEEN) && valueList.contains(CardValue.KING) && valueList.contains(CardValue.NINE) && valueList.contains(CardValue.TEN)) {
            return new Hand(HandType.STRAIGHT, CardValue.KING, CardValue.NONE);
        } else if (valueList.contains(CardValue.JACK) && valueList.contains(CardValue.QUEEN) && valueList.contains(CardValue.EIGHT) && valueList.contains(CardValue.NINE) && valueList.contains(CardValue.TEN)) {
            return new Hand(HandType.STRAIGHT, CardValue.QUEEN, CardValue.NONE);
        } else if (valueList.contains(CardValue.JACK) && valueList.contains(CardValue.SEVEN) && valueList.contains(CardValue.EIGHT) && valueList.contains(CardValue.NINE) && valueList.contains(CardValue.TEN)) {
            return new Hand(HandType.STRAIGHT, CardValue.JACK, CardValue.NONE);
        } else if (valueList.contains(CardValue.SIX) && valueList.contains(CardValue.SEVEN) && valueList.contains(CardValue.EIGHT) && valueList.contains(CardValue.NINE) && valueList.contains(CardValue.TEN)) {
            return new Hand(HandType.STRAIGHT, CardValue.TEN, CardValue.NONE);
        } else if (valueList.contains(CardValue.SIX) && valueList.contains(CardValue.SEVEN) && valueList.contains(CardValue.EIGHT) && valueList.contains(CardValue.NINE) && valueList.contains(CardValue.FIVE)) {
            return new Hand(HandType.STRAIGHT, CardValue.NINE, CardValue.NONE);
        } else if (valueList.contains(CardValue.SIX) && valueList.contains(CardValue.SEVEN) && valueList.contains(CardValue.EIGHT) && valueList.contains(CardValue.FOUR) && valueList.contains(CardValue.FIVE)) {
            return new Hand(HandType.STRAIGHT, CardValue.EIGHT, CardValue.NONE);
        } else if (valueList.contains(CardValue.SIX) && valueList.contains(CardValue.SEVEN) && valueList.contains(CardValue.THREE) && valueList.contains(CardValue.FOUR) && valueList.contains(CardValue.FIVE)) {
            return new Hand(HandType.STRAIGHT, CardValue.SEVEN, CardValue.NONE);
        } else if (valueList.contains(CardValue.SIX) && valueList.contains(CardValue.TWO) && valueList.contains(CardValue.THREE) && valueList.contains(CardValue.FOUR) && valueList.contains(CardValue.FIVE)) {
            return new Hand(HandType.STRAIGHT, CardValue.SIX, CardValue.NONE);
        } else if (valueList.contains(CardValue.ACE) && valueList.contains(CardValue.TWO) && valueList.contains(CardValue.THREE) && valueList.contains(CardValue.FOUR) && valueList.contains(CardValue.FIVE)) {
            return new Hand(HandType.STRAIGHT, CardValue.FIVE, CardValue.NONE);
        } else {
            return null;
        }

    }


    public boolean isFour() {
        List<CardValue> valueList = new ArrayList<>();
        for (Card card : allCards) {
            valueList.add(card.getValue());
        }
        if (Collections.frequency(valueList, CardValue.ACE) == 4) {
            return true;
        } else if (Collections.frequency(valueList, CardValue.KING) == 4) {
            return true;
        } else if (Collections.frequency(valueList, CardValue.QUEEN) == 4) {
            return true;
        } else if (Collections.frequency(valueList, CardValue.JACK) == 4) {
            return true;
        } else if (Collections.frequency(valueList, CardValue.TEN) == 4) {
            return true;
        } else if (Collections.frequency(valueList, CardValue.NINE) == 4) {
            return true;
        } else if (Collections.frequency(valueList, CardValue.EIGHT) == 4) {
            return true;
        } else if (Collections.frequency(valueList, CardValue.SEVEN) == 4) {
            return true;
        } else if (Collections.frequency(valueList, CardValue.SIX) == 4) {
            return true;
        } else if (Collections.frequency(valueList, CardValue.FIVE) == 4) {
            return true;
        } else if (Collections.frequency(valueList, CardValue.FOUR) == 4) {
            return true;
        } else if (Collections.frequency(valueList, CardValue.THREE) == 4) {
            return true;
        } else if (Collections.frequency(valueList, CardValue.TWO) == 4) {
            return true;
        } else {
            return false;
        }
    }

    public Hand valueFour() {
        List<CardValue> valueList = new ArrayList<>();
        for (Card card : allCards) {
            valueList.add(card.getValue());
        }
        if (Collections.frequency(valueList, CardValue.ACE) == 4) {
            return new Hand(HandType.FOUROFAKIND, CardValue.ACE, CardValue.NONE);
        } else if (Collections.frequency(valueList, CardValue.KING) == 4) {
            return new Hand(HandType.FOUROFAKIND, CardValue.KING, CardValue.NONE);
        } else if (Collections.frequency(valueList, CardValue.QUEEN) == 4) {
            return new Hand(HandType.FOUROFAKIND, CardValue.QUEEN, CardValue.NONE);
        } else if (Collections.frequency(valueList, CardValue.JACK) == 4) {
            return new Hand(HandType.FOUROFAKIND, CardValue.JACK, CardValue.NONE);
        } else if (Collections.frequency(valueList, CardValue.TEN) == 4) {
            return new Hand(HandType.FOUROFAKIND, CardValue.TEN, CardValue.NONE);
        } else if (Collections.frequency(valueList, CardValue.NINE) == 4) {
            return new Hand(HandType.FOUROFAKIND, CardValue.NINE, CardValue.NONE);
        } else if (Collections.frequency(valueList, CardValue.EIGHT) == 4) {
            return new Hand(HandType.FOUROFAKIND, CardValue.EIGHT, CardValue.NONE);
        } else if (Collections.frequency(valueList, CardValue.SEVEN) == 4) {
            return new Hand(HandType.FOUROFAKIND, CardValue.SEVEN, CardValue.NONE);
        } else if (Collections.frequency(valueList, CardValue.SIX) == 4) {
            return new Hand(HandType.FOUROFAKIND, CardValue.SIX, CardValue.NONE);
        } else if (Collections.frequency(valueList, CardValue.FIVE) == 4) {
            return new Hand(HandType.FOUROFAKIND, CardValue.FIVE, CardValue.NONE);
        } else if (Collections.frequency(valueList, CardValue.FOUR) == 4) {
            return new Hand(HandType.FOUROFAKIND, CardValue.FOUR, CardValue.NONE);
        } else if (Collections.frequency(valueList, CardValue.THREE) == 4) {
            return new Hand(HandType.FOUROFAKIND, CardValue.THREE, CardValue.NONE);
        } else if (Collections.frequency(valueList, CardValue.TWO) == 4) {
            return new Hand(HandType.FOUROFAKIND, CardValue.TWO, CardValue.NONE);
        } else {
            return null;
        }
    }

    public boolean isTriple() {
        List<CardValue> valueList = new ArrayList<>();
        for (Card card : allCards) {
            valueList.add(card.getValue());
        }
        if (Collections.frequency(valueList, CardValue.ACE) == 3) {
            return true;
        } else if (Collections.frequency(valueList, CardValue.KING) == 3) {
            return true;
        } else if (Collections.frequency(valueList, CardValue.QUEEN) == 3) {
            return true;
        } else if (Collections.frequency(valueList, CardValue.JACK) == 3) {
            return true;
        } else if (Collections.frequency(valueList, CardValue.TEN) == 3) {
            return true;
        } else if (Collections.frequency(valueList, CardValue.NINE) == 3) {
            return true;
        } else if (Collections.frequency(valueList, CardValue.EIGHT) == 3) {
            return true;
        } else if (Collections.frequency(valueList, CardValue.SEVEN) == 3) {
            return true;
        } else if (Collections.frequency(valueList, CardValue.SIX) == 3) {
            return true;
        } else if (Collections.frequency(valueList, CardValue.FIVE) == 3) {
            return true;
        } else if (Collections.frequency(valueList, CardValue.FOUR) == 3) {
            return true;
        } else if (Collections.frequency(valueList, CardValue.THREE) == 3) {
            return true;
        } else if (Collections.frequency(valueList, CardValue.TWO) == 3) {
            return true;
        } else {
            return false;
        }
    }

    public Hand valueTriple() {
        List<CardValue> valueList = new ArrayList<>();
        for (Card card : allCards) {
            valueList.add(card.getValue());
        }
        if (Collections.frequency(valueList, CardValue.ACE) == 3) {
            return new Hand(HandType.THREEOFAKIND, CardValue.ACE, CardValue.NONE);
        } else if (Collections.frequency(valueList, CardValue.KING) == 3) {
            return new Hand(HandType.THREEOFAKIND, CardValue.KING, CardValue.NONE);
        } else if (Collections.frequency(valueList, CardValue.QUEEN) == 3) {
            return new Hand(HandType.THREEOFAKIND, CardValue.QUEEN, CardValue.NONE);
        } else if (Collections.frequency(valueList, CardValue.JACK) == 3) {
            return new Hand(HandType.THREEOFAKIND, CardValue.JACK, CardValue.NONE);
        } else if (Collections.frequency(valueList, CardValue.TEN) == 3) {
            return new Hand(HandType.THREEOFAKIND, CardValue.TEN, CardValue.NONE);
        } else if (Collections.frequency(valueList, CardValue.NINE) == 3) {
            return new Hand(HandType.THREEOFAKIND, CardValue.NINE, CardValue.NONE);
        } else if (Collections.frequency(valueList, CardValue.EIGHT) == 3) {
            return new Hand(HandType.THREEOFAKIND, CardValue.EIGHT, CardValue.NONE);
        } else if (Collections.frequency(valueList, CardValue.SEVEN) == 3) {
            return new Hand(HandType.THREEOFAKIND, CardValue.SEVEN, CardValue.NONE);
        } else if (Collections.frequency(valueList, CardValue.SIX) == 3) {
            return new Hand(HandType.THREEOFAKIND, CardValue.SIX, CardValue.NONE);
        } else if (Collections.frequency(valueList, CardValue.FIVE) == 3) {
            return new Hand(HandType.THREEOFAKIND, CardValue.FIVE, CardValue.NONE);
        } else if (Collections.frequency(valueList, CardValue.FOUR) == 3) {
            return new Hand(HandType.THREEOFAKIND, CardValue.FOUR, CardValue.NONE);
        } else if (Collections.frequency(valueList, CardValue.THREE) == 3) {
            return new Hand(HandType.THREEOFAKIND, CardValue.THREE, CardValue.NONE);
        } else if (Collections.frequency(valueList, CardValue.TWO) == 3) {
            return new Hand(HandType.THREEOFAKIND, CardValue.TWO, CardValue.NONE);
        } else {
            return null;
        }
    }

    public CardValue valueTripleCardValue() {
        List<CardValue> valueList = new ArrayList<>();
        for (Card card : allCards) {
            valueList.add(card.getValue());
        }
        if (Collections.frequency(valueList, CardValue.ACE) == 3) {
            return CardValue.ACE;
        } else if (Collections.frequency(valueList, CardValue.KING) == 3) {
            return CardValue.KING;
        } else if (Collections.frequency(valueList, CardValue.QUEEN) == 3) {
            return CardValue.QUEEN;
        } else if (Collections.frequency(valueList, CardValue.JACK) == 3) {
            return CardValue.JACK;
        } else if (Collections.frequency(valueList, CardValue.TEN) == 3) {
            return CardValue.TEN;
        } else if (Collections.frequency(valueList, CardValue.NINE) == 3) {
            return CardValue.NINE;
        } else if (Collections.frequency(valueList, CardValue.EIGHT) == 3) {
            return CardValue.EIGHT;
        } else if (Collections.frequency(valueList, CardValue.SEVEN) == 3) {
            return CardValue.SEVEN;
        } else if (Collections.frequency(valueList, CardValue.SIX) == 3) {
            return CardValue.SIX;
        } else if (Collections.frequency(valueList, CardValue.FIVE) == 3) {
            return CardValue.FIVE;
        } else if (Collections.frequency(valueList, CardValue.FOUR) == 3) {
            return CardValue.FOUR;
        } else if (Collections.frequency(valueList, CardValue.THREE) == 3) {
            return CardValue.THREE;
        } else if (Collections.frequency(valueList, CardValue.TWO) == 3) {
            return CardValue.TWO;
        } else {
            return null;
        }
    }

    public boolean isPair() {
        List<CardValue> valueList = new ArrayList<>();
        for (Card card : allCards) {
            valueList.add(card.getValue());
        }
        if (Collections.frequency(valueList, CardValue.ACE) == 2) {
            return true;
        } else if (Collections.frequency(valueList, CardValue.KING) == 2) {
            return true;
        } else if (Collections.frequency(valueList, CardValue.QUEEN) == 2) {
            return true;
        } else if (Collections.frequency(valueList, CardValue.JACK) == 2) {
            return true;
        } else if (Collections.frequency(valueList, CardValue.TEN) == 2) {
            return true;
        } else if (Collections.frequency(valueList, CardValue.NINE) == 2) {
            return true;
        } else if (Collections.frequency(valueList, CardValue.EIGHT) == 2) {
            return true;
        } else if (Collections.frequency(valueList, CardValue.SEVEN) == 2) {
            return true;
        } else if (Collections.frequency(valueList, CardValue.SIX) == 2) {
            return true;
        } else if (Collections.frequency(valueList, CardValue.FIVE) == 2) {
            return true;
        } else if (Collections.frequency(valueList, CardValue.FOUR) == 2) {
            return true;
        } else if (Collections.frequency(valueList, CardValue.THREE) == 2) {
            return true;
        } else if (Collections.frequency(valueList, CardValue.TWO) == 2) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isTwoPair() {
        List<CardValue> valueList = new ArrayList<>();
        for (Card card : allCards) {
            valueList.add(card.getValue());
        }
        int counter = 0;
        if (Collections.frequency(valueList, CardValue.ACE) == 2) {
            counter += 1;
        } else if (Collections.frequency(valueList, CardValue.KING) == 2) {
            counter += 1;
        } else if (Collections.frequency(valueList, CardValue.QUEEN) == 2) {
            counter += 1;
        } else if (Collections.frequency(valueList, CardValue.JACK) == 2) {
            counter += 1;
        } else if (Collections.frequency(valueList, CardValue.TEN) == 2) {
            counter += 1;
        } else if (Collections.frequency(valueList, CardValue.NINE) == 2) {
            counter += 1;
        } else if (Collections.frequency(valueList, CardValue.EIGHT) == 2) {
            counter += 1;
        } else if (Collections.frequency(valueList, CardValue.SEVEN) == 2) {
            counter += 1;
        } else if (Collections.frequency(valueList, CardValue.SIX) == 2) {
            counter += 1;
        } else if (Collections.frequency(valueList, CardValue.FIVE) == 2) {
            counter += 1;
        } else if (Collections.frequency(valueList, CardValue.FOUR) == 2) {
            counter += 1;
        } else if (Collections.frequency(valueList, CardValue.THREE) == 2) {
            counter += 1;
        } else if (Collections.frequency(valueList, CardValue.TWO) == 2) {
            counter += 1;
        }
        if (counter > 1) {
            return true;
        } else {
            return false;
        }
    }

    public Hand valueTwoPair() {
        List<CardValue> valueList = new ArrayList<>();
        List<CardValue> list = new ArrayList<>();
        for (Card card : allCards) {
            valueList.add(card.getValue());
        }
        if (Collections.frequency(valueList, CardValue.ACE) == 2) {
            list.add(CardValue.ACE);
        } else if (Collections.frequency(valueList, CardValue.KING) == 2) {
            list.add(CardValue.KING);
        } else if (Collections.frequency(valueList, CardValue.QUEEN) == 2) {
            list.add(CardValue.QUEEN);
        } else if (Collections.frequency(valueList, CardValue.JACK) == 2) {
            list.add(CardValue.JACK);
        } else if (Collections.frequency(valueList, CardValue.TEN) == 2) {
            list.add(CardValue.TEN);
        } else if (Collections.frequency(valueList, CardValue.NINE) == 2) {
            list.add(CardValue.NINE);
        } else if (Collections.frequency(valueList, CardValue.EIGHT) == 2) {
            list.add(CardValue.EIGHT);
        } else if (Collections.frequency(valueList, CardValue.SEVEN) == 2) {
            list.add(CardValue.SEVEN);
        } else if (Collections.frequency(valueList, CardValue.SIX) == 2) {
            list.add(CardValue.SIX);
        } else if (Collections.frequency(valueList, CardValue.FIVE) == 2) {
            list.add(CardValue.FIVE);
        } else if (Collections.frequency(valueList, CardValue.FOUR) == 2) {
            list.add(CardValue.FOUR);
        } else if (Collections.frequency(valueList, CardValue.THREE) == 2) {
            list.add(CardValue.THREE);
        } else if (Collections.frequency(valueList, CardValue.TWO) == 2) {
            list.add(CardValue.TWO);
        }
        return new Hand(HandType.TWOPAIR, list.get(0), list.get(1));
    }

    public boolean isTwoTriple() {
        int counter = 0;
        List<CardValue> valueList = new ArrayList<>();
        for (Card card : allCards) {
            valueList.add(card.getValue());
        }
        if (Collections.frequency(valueList, CardValue.ACE) == 3) {
            counter += 1;
        } else if (Collections.frequency(valueList, CardValue.KING) == 3) {
            counter += 1;
        } else if (Collections.frequency(valueList, CardValue.QUEEN) == 3) {
            counter += 1;
        } else if (Collections.frequency(valueList, CardValue.JACK) == 3) {
            counter += 1;
        } else if (Collections.frequency(valueList, CardValue.TEN) == 3) {
            counter += 1;
        } else if (Collections.frequency(valueList, CardValue.NINE) == 3) {
            counter += 1;
        } else if (Collections.frequency(valueList, CardValue.EIGHT) == 3) {
            counter += 1;
        } else if (Collections.frequency(valueList, CardValue.SEVEN) == 3) {
            counter += 1;
        } else if (Collections.frequency(valueList, CardValue.SIX) == 3) {
            counter += 1;
        } else if (Collections.frequency(valueList, CardValue.FIVE) == 3) {
            counter += 1;
        } else if (Collections.frequency(valueList, CardValue.FOUR) == 3) {
            counter += 1;
        } else if (Collections.frequency(valueList, CardValue.THREE) == 3) {
            counter += 1;
        } else if (Collections.frequency(valueList, CardValue.TWO) == 3) {
            counter += 1;
        }
        if (counter > 1) {
            return true;
        } else {
            return false;
        }
    }

    public Hand valueTwoTriple() {
        List<CardValue> valueList = new ArrayList<>();
        List<CardValue> list = new ArrayList<>(); //if there is a triple, is added
        for (Card card : allCards) {
            valueList.add(card.getValue());
        }
        if (Collections.frequency(valueList, CardValue.ACE) == 3) {
            list.add(CardValue.ACE);
        } else if (Collections.frequency(valueList, CardValue.KING) == 3) {
            list.add(CardValue.KING);
        } else if (Collections.frequency(valueList, CardValue.QUEEN) == 3) {
            list.add(CardValue.QUEEN);
        } else if (Collections.frequency(valueList, CardValue.JACK) == 3) {
            list.add(CardValue.JACK);
        } else if (Collections.frequency(valueList, CardValue.TEN) == 3) {
            list.add(CardValue.TEN);
        } else if (Collections.frequency(valueList, CardValue.NINE) == 3) {
            list.add(CardValue.NINE);
        } else if (Collections.frequency(valueList, CardValue.EIGHT) == 3) {
            list.add(CardValue.EIGHT);
        } else if (Collections.frequency(valueList, CardValue.SEVEN) == 3) {
            list.add(CardValue.SEVEN);
        } else if (Collections.frequency(valueList, CardValue.SIX) == 3) {
            list.add(CardValue.SIX);
        } else if (Collections.frequency(valueList, CardValue.FIVE) == 3) {
            list.add(CardValue.FIVE);
        } else if (Collections.frequency(valueList, CardValue.FOUR) == 3) {
            list.add(CardValue.FOUR);
        } else if (Collections.frequency(valueList, CardValue.THREE) == 3) {
            list.add(CardValue.THREE);
        } else if (Collections.frequency(valueList, CardValue.TWO) == 3) {
            list.add(CardValue.TWO);
        }
        return new Hand(HandType.FULLHOUSE, list.get(0), list.get(1));
    }


    public boolean isHouse() {
        if ((this.isTriple() && this.isPair()) || this.isTwoTriple()) {
            return true;
        } else {
            return false;
        }
    }

    public Hand valueHouse() {
        if (this.isTriple() && this.isPair()) {
            return new Hand(HandType.FULLHOUSE, this.valueTripleCardValue(), this.valuePairCardValue());
        } else {
            return this.valueTwoTriple();
        }
    }

    public Hand valuePair() {
        List<CardValue> valueList = new ArrayList<>();
        for (Card card : allCards) {
            valueList.add(card.getValue());
        }
        if (Collections.frequency(valueList, CardValue.ACE) == 2) {
            return new Hand(HandType.ONEPAIR, CardValue.ACE, CardValue.NONE);
        } else if (Collections.frequency(valueList, CardValue.KING) == 2) {
            return new Hand(HandType.ONEPAIR, CardValue.KING, CardValue.NONE);
        } else if (Collections.frequency(valueList, CardValue.QUEEN) == 2) {
            return new Hand(HandType.ONEPAIR, CardValue.QUEEN, CardValue.NONE);
        } else if (Collections.frequency(valueList, CardValue.JACK) == 2) {
            return new Hand(HandType.ONEPAIR, CardValue.JACK, CardValue.NONE);
        } else if (Collections.frequency(valueList, CardValue.TEN) == 2) {
            return new Hand(HandType.ONEPAIR, CardValue.TEN, CardValue.NONE);
        } else if (Collections.frequency(valueList, CardValue.NINE) == 2) {
            return new Hand(HandType.ONEPAIR, CardValue.NINE, CardValue.NONE);
        } else if (Collections.frequency(valueList, CardValue.EIGHT) == 2) {
            return new Hand(HandType.ONEPAIR, CardValue.EIGHT, CardValue.NONE);
        } else if (Collections.frequency(valueList, CardValue.SEVEN) == 2) {
            return new Hand(HandType.ONEPAIR, CardValue.SEVEN, CardValue.NONE);
        } else if (Collections.frequency(valueList, CardValue.SIX) == 2) {
            return new Hand(HandType.ONEPAIR, CardValue.SIX, CardValue.NONE);
        } else if (Collections.frequency(valueList, CardValue.FIVE) == 2) {
            return new Hand(HandType.ONEPAIR, CardValue.FIVE, CardValue.NONE);
        } else if (Collections.frequency(valueList, CardValue.FOUR) == 2) {
            return new Hand(HandType.ONEPAIR, CardValue.FOUR, CardValue.NONE);
        } else if (Collections.frequency(valueList, CardValue.THREE) == 2) {
            return new Hand(HandType.ONEPAIR, CardValue.THREE, CardValue.NONE);
        } else if (Collections.frequency(valueList, CardValue.TWO) == 2) {
            return new Hand(HandType.ONEPAIR, CardValue.TWO, CardValue.NONE);
        } else {
            return null;
        }
    }

    public CardValue valuePairCardValue() {
        List<CardValue> valueList = new ArrayList<>();
        for (Card card : allCards) {
            valueList.add(card.getValue());
        }
        if (Collections.frequency(valueList, CardValue.ACE) == 2) {
            return CardValue.ACE;
        } else if (Collections.frequency(valueList, CardValue.KING) == 2) {
            return CardValue.KING;
        } else if (Collections.frequency(valueList, CardValue.QUEEN) == 2) {
            return CardValue.QUEEN;
        } else if (Collections.frequency(valueList, CardValue.JACK) == 2) {
            return CardValue.JACK;
        } else if (Collections.frequency(valueList, CardValue.TEN) == 2) {
            return CardValue.TEN;
        } else if (Collections.frequency(valueList, CardValue.NINE) == 2) {
            return CardValue.NINE;
        } else if (Collections.frequency(valueList, CardValue.EIGHT) == 2) {
            return CardValue.EIGHT;
        } else if (Collections.frequency(valueList, CardValue.SEVEN) == 2) {
            return CardValue.SEVEN;
        } else if (Collections.frequency(valueList, CardValue.SIX) == 2) {
            return CardValue.SIX;
        } else if (Collections.frequency(valueList, CardValue.FIVE) == 2) {
            return CardValue.FIVE;
        } else if (Collections.frequency(valueList, CardValue.FOUR) == 2) {
            return CardValue.FOUR;
        } else if (Collections.frequency(valueList, CardValue.THREE) == 2) {
            return CardValue.THREE;
        } else if (Collections.frequency(valueList, CardValue.TWO) == 2) {
            return CardValue.TWO;
        } else {
            return null;
        }
    }

    public Hand valueHigh() {
        List<CardValue> valueList = new ArrayList<>();
        for (Card card : allCards) {
            valueList.add(card.getValue());
        }
        if (Collections.frequency(valueList, CardValue.ACE) == 1) {
            return new Hand(HandType.HIGHCARD, CardValue.ACE, CardValue.NONE);
        } else if (Collections.frequency(valueList, CardValue.KING) == 1) {
            return new Hand(HandType.HIGHCARD, CardValue.KING, CardValue.NONE);
        } else if (Collections.frequency(valueList, CardValue.QUEEN) == 1) {
            return new Hand(HandType.HIGHCARD, CardValue.QUEEN, CardValue.NONE);
        } else if (Collections.frequency(valueList, CardValue.JACK) == 1) {
            return new Hand(HandType.HIGHCARD, CardValue.JACK, CardValue.NONE);
        } else if (Collections.frequency(valueList, CardValue.TEN) == 1) {
            return new Hand(HandType.HIGHCARD, CardValue.TEN, CardValue.NONE);
        } else if (Collections.frequency(valueList, CardValue.NINE) == 1) {
            return new Hand(HandType.HIGHCARD, CardValue.NINE, CardValue.NONE);
        } else if (Collections.frequency(valueList, CardValue.EIGHT) == 1) {
            return new Hand(HandType.HIGHCARD, CardValue.EIGHT, CardValue.NONE);
        } else if (Collections.frequency(valueList, CardValue.SEVEN) == 1) {
            return new Hand(HandType.HIGHCARD, CardValue.SEVEN, CardValue.NONE);
        } else if (Collections.frequency(valueList, CardValue.SIX) == 1) {
            return new Hand(HandType.HIGHCARD, CardValue.SIX, CardValue.NONE);
        } else if (Collections.frequency(valueList, CardValue.FIVE) == 1) {
            return new Hand(HandType.HIGHCARD, CardValue.FIVE, CardValue.NONE);
        } else if (Collections.frequency(valueList, CardValue.FOUR) == 1) {
            return new Hand(HandType.HIGHCARD, CardValue.FOUR, CardValue.NONE);
        } else if (Collections.frequency(valueList, CardValue.THREE) == 1) {
            return new Hand(HandType.HIGHCARD, CardValue.THREE, CardValue.NONE);
        } else if (Collections.frequency(valueList, CardValue.TWO) == 1) {
            return new Hand(HandType.HIGHCARD, CardValue.TWO, CardValue.NONE);
        } else {
            return null;
        }
    }

    public Hand returnHand() {
        if (isStraightFlush()) {
            return valueStraightFlush();
        } else if (isFour()) {
            return valueFour();
        } else if (isHouse()) {
            return valueHouse();
        } else if (isFlush()) {
            return valueFlush();
        } else if (isStraight()) {
            return valueOfStraight();
        } else if (isTriple()) {
            return valueTriple();
        } else if (isTwoPair()) {
            return valueTwoPair();
        } else if (isPair()) {
            return valuePair();
        } else {
            return valueHigh();
        }
    }
}



