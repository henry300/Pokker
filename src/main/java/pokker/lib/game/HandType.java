package pokker.lib.game;

import java.util.Collections;
import java.util.List;

public enum HandType {
    STRAIGHTFLUSH {
        @Override
        public boolean isFullhand(FullHand hand) {
            return FLUSH.isFullhand(hand) && STRAIGHT.isFullhand(hand);
        }
    },
    FOUROFAKIND {
        @Override
        public boolean isFullhand(FullHand hand) {
            List<Card> allCards = hand.getAllCards();
            Collections.sort(allCards);

            return hasXOfSame(4, allCards) != null;
        }
    },
    FULLHOUSE {
        @Override
        public boolean isFullhand(FullHand hand) {
            List<Card> allCards = hand.getAllCards();
            Collections.sort(allCards);

            CardValue threeOfAKind = hasXOfSame(3, allCards);
            if (threeOfAKind == null) {
                return false;
            }

            Collections.reverse(allCards);

            return hasXOfSame(2, allCards) != threeOfAKind;
        }
    },
    FLUSH {
        @Override
        public boolean isFullhand(FullHand hand) {
            List<Card> allCards = hand.getAllCards();

            int amntOfSameSuit[] = new int[CardSuit.values().length];

            for (Card card : allCards) {
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
        public boolean isFullhand(FullHand hand) {
            List<Card> allCards = hand.getAllCards();
            Collections.sort(allCards);

            int cardsAmnt = allCards.size();
            int descendingCardsInARow = 0;

            for (int i = 1; i < cardsAmnt; i++) {
                Card currentCard = allCards.get(i);
                Card previousCard = allCards.get(i - 1);

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
        public boolean isFullhand(FullHand hand) {
            List<Card> allCards = hand.getAllCards();
            Collections.sort(allCards);

            return hasXOfSame(3, allCards) != null;
        }
    },
    TWOPAIR {
        @Override
        public boolean isFullhand(FullHand hand) {
            List<Card> allCards = hand.getAllCards();
            Collections.sort(allCards);

            CardValue pair1 = hasXOfSame(2, allCards);

            Collections.reverse(allCards);

            return pair1 != hasXOfSame(2, allCards);
        }
    },
    ONEPAIR {
        @Override
        public boolean isFullhand(FullHand hand) {
            List<Card> allCards = hand.getAllCards();
            Collections.sort(allCards);

            return hasXOfSame(2, allCards) != null;
        }
    },
    HIGHCARD {
        @Override
        public boolean isFullhand(FullHand hand) {
            return true; // a hand is always going to be a "high card" - type of hand
        }
    };

    /**
     * Checks whether full hand is of this type
     *
     * @param hand
     * @return
     */
    public abstract boolean isFullhand(FullHand hand);

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
