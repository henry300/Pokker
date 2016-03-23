package main.java.pokker;

public class Card {
    private final byte suit;
    private final byte value;

    Card(byte suit, byte value) {
        if (suit > 4 || suit < 1) {

        }
        this.suit = suit;
        this.value = value;
    }

    int getSuit() {
        return suit;
    }

    String getSuitAsString() {
        switch (suit) {
            case 1:
                return "spades";
            case 2:
                return "hearts";
            case 3:
                return "diamonds";
            case 4:
                return "clubs";
        }
    }
}
