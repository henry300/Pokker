package pokker.lib.hands;

import org.junit.Test;
import static org.junit.Assert.*;
import pokker.lib.game.card.Card;
import pokker.lib.game.card.CardSuit;
import pokker.lib.game.card.CardValue;
import pokker.lib.game.hands.Hand;
import pokker.lib.game.hands.FullHandFactory;
import pokker.lib.game.hands.HandType;

import java.util.Arrays;

public class FullHandFactoryTest {
    private final FullHandFactory handFactory = new FullHandFactory();

    @Test
    public void testStraightFlush() {
        Hand hand = handFactory.createHand(Arrays.asList(
                new Card(CardSuit.SPADES, CardValue.ACE),
                new Card(CardSuit.CLUBS, CardValue.ACE),
                new Card(CardSuit.CLUBS, CardValue.KING),
                new Card(CardSuit.SPADES, CardValue.QUEEN),
                new Card(CardSuit.CLUBS, CardValue.QUEEN),
                new Card(CardSuit.CLUBS, CardValue.JACK),
                new Card(CardSuit.CLUBS, CardValue.TEN)
        ));

        assertEquals(hand.getHandType(), HandType.STRAIGHTFLUSH);
    }

    @Test
    public void testFourOfAKind() {
        Hand hand = handFactory.createHand(Arrays.asList(
                new Card(CardSuit.CLUBS, CardValue.QUEEN),
                new Card(CardSuit.SPADES, CardValue.JACK),
                new Card(CardSuit.DIAMONDS, CardValue.FIVE),
                new Card(CardSuit.CLUBS, CardValue.FIVE),
                new Card(CardSuit.HEARTS, CardValue.FIVE),
                new Card(CardSuit.SPADES, CardValue.FIVE),
                new Card(CardSuit.CLUBS, CardValue.FOUR)
        ));

        assertEquals(hand.getHandType(), HandType.FOUROFAKIND);
    }

    @Test
    public void testFullHouse() {
        Hand hand = handFactory.createHand(Arrays.asList(
                new Card(CardSuit.DIAMONDS, CardValue.ACE),
                new Card(CardSuit.CLUBS, CardValue.QUEEN),
                new Card(CardSuit.DIAMONDS, CardValue.QUEEN),
                new Card(CardSuit.SPADES, CardValue.QUEEN),
                new Card(CardSuit.SPADES, CardValue.FIVE),
                new Card(CardSuit.CLUBS, CardValue.FIVE),
                new Card(CardSuit.HEARTS, CardValue.FOUR)
        ));

        assertEquals(hand.getHandType(), HandType.FULLHOUSE);
    }

    @Test
    public void testFlush() {
        Hand hand = handFactory.createHand(Arrays.asList(
                new Card(CardSuit.SPADES, CardValue.ACE),
                new Card(CardSuit.CLUBS, CardValue.ACE),
                new Card(CardSuit.SPADES, CardValue.KING),
                new Card(CardSuit.CLUBS, CardValue.QUEEN),
                new Card(CardSuit.CLUBS, CardValue.JACK),
                new Card(CardSuit.CLUBS, CardValue.TEN),
                new Card(CardSuit.CLUBS, CardValue.NINE)
        ));

        assertEquals(hand.getHandType(), HandType.FLUSH);
    }

    @Test
    public void testStraight() {
        Hand hand = handFactory.createHand(Arrays.asList(
                new Card(CardSuit.SPADES, CardValue.ACE),
                new Card(CardSuit.SPADES, CardValue.TEN),
                new Card(CardSuit.HEARTS, CardValue.SEVEN),
                new Card(CardSuit.CLUBS, CardValue.FIVE),
                new Card(CardSuit.CLUBS, CardValue.FOUR),
                new Card(CardSuit.DIAMONDS, CardValue.THREE),
                new Card(CardSuit.DIAMONDS, CardValue.TWO)
        ));

        assertEquals(hand.getHandType(), HandType.STRAIGHT);
    }

    @Test
    public void testThreeOfAKind() {
        Hand hand = handFactory.createHand(Arrays.asList(
                new Card(CardSuit.CLUBS, CardValue.KING),
                new Card(CardSuit.CLUBS, CardValue.JACK),
                new Card(CardSuit.HEARTS, CardValue.EIGHT),
                new Card(CardSuit.CLUBS, CardValue.SIX),
                new Card(CardSuit.DIAMONDS, CardValue.SIX),
                new Card(CardSuit.SPADES, CardValue.SIX),
                new Card(CardSuit.CLUBS, CardValue.TWO)
        ));

        assertEquals(hand.getHandType(), HandType.THREEOFAKIND);
    }

    @Test
    public void testTwoPair() {
        Hand hand = handFactory.createHand(Arrays.asList(
                new Card(CardSuit.CLUBS, CardValue.KING),
                new Card(CardSuit.HEARTS, CardValue.KING),
                new Card(CardSuit.SPADES, CardValue.TEN),
                new Card(CardSuit.SPADES, CardValue.EIGHT),
                new Card(CardSuit.CLUBS, CardValue.FIVE),
                new Card(CardSuit.CLUBS, CardValue.TWO),
                new Card(CardSuit.DIAMONDS, CardValue.TWO)
        ));

        assertEquals(hand.getHandType(), HandType.TWOPAIR);
    }

    @Test
    public void testOnePair() {
         Hand hand = handFactory.createHand(Arrays.asList(
                new Card(CardSuit.CLUBS, CardValue.KING),
                new Card(CardSuit.HEARTS, CardValue.QUEEN),
                new Card(CardSuit.SPADES, CardValue.TEN),
                new Card(CardSuit.SPADES, CardValue.EIGHT),
                new Card(CardSuit.CLUBS, CardValue.FIVE),
                new Card(CardSuit.CLUBS, CardValue.TWO),
                new Card(CardSuit.DIAMONDS, CardValue.TWO)
        ));

        assertEquals(hand.getHandType(), HandType.ONEPAIR);
    }

    @Test
    public void testHighCard() {
        // TODO: test a bunch of junk-hands
    }
}
