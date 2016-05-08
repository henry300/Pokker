package pokker.lib.game;

import java.util.Collections;
import java.util.List;

public enum HandType {
    STRAIGHTFLUSH {
        @Override
        public boolean areCardsThisType(List<Card> cards) {
            return FLUSH.areCardsThisType(cards) && STRAIGHT.areCardsThisType(cards);
        }
    },
    FOUROFAKIND {
        @Override
        public boolean areCardsThisType(List<Card> cards) {
            return hasXOfSame(4, cards) != null;
        }
    },
    FULLHOUSE {
        @Override
        public boolean areCardsThisType(List<Card> cards) {
            CardValue threeOfAKind = hasXOfSame(3, cards);
            if (threeOfAKind == null) {
                return false;
            }

            Collections.reverse(cards);

            return hasXOfSame(2, cards) != threeOfAKind;
        }
    },
    FLUSH {
        @Override
        public boolean areCardsThisType(List<Card> cards) {
            int amntOfSameSuit[] = new int[CardSuit.values().length];

            for (Card card : cards) {
                amntOfSameSuit[card.getSuit().ordinal()]++;
            }

            for (int i : amntOfSameSuit) {
                if (i >= 5) return true;
            }

            return false;
        }
    },
    STRAIGHT {
        @Override
        public boolean areCardsThisType(List<Card> cards) {
            int cardsAmnt = cards.size();
            int descendingCardsInARow = 0;

            for (int i = 1; i < cardsAmnt; i++) {
                Card currentCard = cards.get(i);
                Card previousCard = cards.get(i - 1);

                if (currentCard.getValue().ordinal() == previousCard.getValue().ordinal() + 1) {
                    if (++descendingCardsInARow == 5) {
                        return true;
                    }
                } else if (cardsAmnt - i < 5) {
                    return false;
                } else {
                    descendingCardsInARow = 0;
                }
            }

            return false;
        }
    },
    THREEOFAKIND {
        @Override
        public boolean areCardsThisType(List<Card> cards) {
            return hasXOfSame(3, cards) != null;
        }
    },
    TWOPAIR {
        @Override
        public boolean areCardsThisType(List<Card> cards) {
            CardValue pair1 = hasXOfSame(2, cards);

            Collections.reverse(cards);

            return pair1 != hasXOfSame(2, cards);
        }
    },
    ONEPAIR {
        @Override
        public boolean areCardsThisType(List<Card> cards) {
            return hasXOfSame(2, cards) != null;
        }
    },
    HIGHCARD {
        @Override
        public boolean areCardsThisType(List<Card> cards) {
            return true; // a hand is always going to be a "high card" - type of hand
        }
    };

    /**
     * Checks whether a list of cards is this type
     *
     * @param cards List of *sorted* cards
     * @return
     */
    public abstract boolean areCardsThisType(List<Card> cards);

    /**
     * Checks if a list of cards has the same type of card X times.
     * This is useful to check pairs, three of a kinds and four of a kinds, simply by changing X
     * E.g: three of a kind: X = 3; four of a kind: X = 4
     *
     * @param x
     * @param cards a *SORTED* list of cards
     * @return Value of the card if a value was found X times, else null
     */
    protected CardValue hasXOfSame(int x, List<Card> cards) {
        int cardsAmnt = cards.size();
        int numInARow = 0;
        for (int i = 1; i < cardsAmnt; i++) {
            Card currentCard = cards.get(i);
            Card previousCard = cards.get(i - 1);
            if (currentCard.getValue() == previousCard.getValue()) {
                if (++numInARow == x) {
                    return currentCard.getValue();
                }
            } else if (cardsAmnt - i < x) {
                return null;
            } else {
                numInARow = 0;
            }
        }

        return null;
    }
}
