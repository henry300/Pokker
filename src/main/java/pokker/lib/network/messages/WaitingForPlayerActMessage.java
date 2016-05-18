package pokker.lib.network.messages;

public class WaitingForPlayerActMessage extends PlayerMessage {
    public WaitingForPlayerActMessage(int tableId, int playerPos) {
        super(tableId, playerPos);
    }

    public MessageContainer createContainedMessage() {
        return super.createContainedMessage(MessageType.WaitingForPlayerAct);
    }
}
