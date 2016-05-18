package pokker.lib.network.messages;

public abstract class ContainableMessage {
    public MessageContainer createContainedMessage(MessageType messageType) {
        return new MessageContainer(messageType, this);
    }
}
