package pokker;

import java.util.HashMap;
import java.util.Map;

public class Card{
    final CardSuit suit;
    final CardValue value;

    Card(CardSuit suit, CardValue value) {
        this.suit = suit;
        this.value = value;
    }

}
