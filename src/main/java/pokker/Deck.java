package main.java.pokker;

import java.util.LinkedList;
import java.util.Queue;

public class Deck {
    private final static Card[][] allCards = new Card[4][13];
    private final Queue<Card> cardsInDeck = new LinkedList<>();

    Deck(){
        for (Card[] cards:allCards) {
            for (Card card :
                    cards) {
                cardsInDeck.add(card);
            }
        }
    }

    private void shuffle(){

    }
}
