package pokker.lib.network.messages;

import com.google.gson.annotations.Expose;
import pokker.lib.game.card.Card;

import java.util.List;

public class CardsDealtOnTableMessage extends TableMessage {
    @Expose
    private final List<Card> cards;

    public CardsDealtOnTableMessage(int tableId, List<Card> cards) {
        super(tableId);
        this.cards = cards;
    }

    public List<Card> getCards() {
        return cards;
    }

    public MessageContainer createContainedMessage() {
        return new MessageContainer(MessageType.CardsDealtOnTable, this);
    }
}
