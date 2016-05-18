package pokker.lib.network.messages;

import com.google.gson.annotations.Expose;

public class ActMessage extends TableMessage {
    @Expose
    private final int bet;

    public ActMessage(int tableId, int bet) {
        super(tableId);

        this.bet = bet;
    }

    public int getBet() {
        return bet;
    }

    public MessageContainer createContainedMessage() {
        return super.createContainedMessage(MessageType.PlayerAct);
    }
}
