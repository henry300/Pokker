package pokker;

public class Card {
    private final CardSuit suit;
    private final CardValue value;

    Card(CardSuit suit, CardValue value) {
        this.suit = suit;
        this.value = value;
    }
}
