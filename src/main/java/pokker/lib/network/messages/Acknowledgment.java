package pokker.lib.network.messages;

public class Acknowledgment extends MessageContainer {
    public Acknowledgment(int id) {
        super(MessageType.Acknowledgment, id);
    }
}
