package pokker.lib.network.messages;

public class Acknowledgment extends Message {
    public Acknowledgment(int hash) {
        super(MessageType.Acknowledgment, hash);
    }
}
