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
        if (diamonds > 4 || clubs > 4 || spades > 4 || hearts > 4) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isStraight() {
        List<CardSuit> suitList = new ArratList<>();
        for (Card card : allCards) {
            suitList.add(card.suit);
        }
        if (list.contains(CardValue.ACE) && list.contains(CardValue.TWO) && list.contains(CardValue.THREE) && list.contains(CardValue.FOUR) && list.contains(CardValue.FIVE)) {
            return true;
        } else if (list.contains(CardValue.SIX) && list.contains(CardValue.TWO) && list.contains(CardValue.THREE) && list.contains(CardValue.FOUR) && list.contains(CardValue.FIVE)) {
            return true;
        } else if (list.contains(CardValue.SIX) && list.contains(CardValue.SEVEN) && list.contains(CardValue.THREE) && list.contains(CardValue.FOUR) && list.contains(CardValue.FIVE)) {
            return true;
        } else if (list.contains(CardValue.SIX) && list.contains(CardValue.SEVEN) && list.contains(CardValue.EIGHT) && list.contains(CardValue.FOUR) && list.contains(CardValue.FIVE)) {
            return true;
        } else if (list.contains(CardValue.SIX) && list.contains(CardValue.SEVEN) && list.contains(CardValue.EIGHT) && list.contains(CardValue.NINE) && list.contains(CardValue.FIVE)) {
            return true;
        } else if (list.contains(CardValue.SIX) && list.contains(CardValue.SEVEN) && list.contains(CardValue.EIGHT) && list.contains(CardValue.NINE) && list.contains(CardValue.TEN)) {
            return true;
        } else if (list.contains(CardValue.JACK) && list.contains(CardValue.SEVEN) && list.contains(CardValue.EIGHT) && list.contains(CardValue.NINE) && list.contains(CardValue.TEN)) {
            return true;
        } else if (list.contains(CardValue.JACK) && list.contains(CardValue.QUEEN) && list.contains(CardValue.EIGHT) && list.contains(CardValue.NINE) && list.contains(CardValue.TEN)) {
            return true;
        } else if (list.contains(CardValue.JACK) && list.contains(CardValue.QUEEN) && list.contains(CardValue.KING) && list.contains(CardValue.NINE) && list.contains(CardValue.TEN)) {
            return true;
        } else if (list.contains(CardValue.JACK) && list.contains(CardValue.QUEEN) && list.contains(CardValue.KING) && list.contains(CardValue.ACE) && list.contains(CardValue.TEN)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isFour() {
        List<CardSuit> suitList = new ArratList<>();
        for (Card card : allCards) {
            suitList.add(card.suit);
        }
        if (Collections.frequency(suitList, CardValue.ACE) > 3) {
            return true;
        } else if (Collections.frequency(suitList, CardValue.KING) > 3) {
            return true;
        } else if (Collections.frequency(suitList, CardValue.QUEEN) > 3) {
            return true;
        } else if (Collections.frequency(suitList, CardValue.JACK) > 3) {
            return true;
        } else if (Collections.frequency(suitList, CardValue.TEN) > 3) {
            return true;
        } else if (Collections.frequency(suitList, CardValue.NINE) > 3) {
            return true;
        } else if (Collections.frequency(suitList, CardValue.EIGHT) > 3) {
            return true;
        } else if (Collections.frequency(suitList, CardValue.SEVEN) > 3) {
            return true;
        } else if (Collections.frequency(suitList, CardValue.SIX) > 3) {
            return true;
        } else if (Collections.frequency(suitList, CardValue.FIVE) > 3) {
            return true;
        } else if (Collections.frequency(suitList, CardValue.FOUR) > 3) {
            return true;
        } else if (Collections.frequency(suitList, CardValue.THREE) > 3) {
            return true;
        } else if (Collections.frequency(suitList, CardValue.TWO) > 3) {
            return true;
        } else {
            return false;
        }
    }
}



