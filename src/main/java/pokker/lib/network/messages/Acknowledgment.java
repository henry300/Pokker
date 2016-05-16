package pokker.lib.network.messages;

public class Acknowledgment extends Message {
    public Acknowledgment(int id) {
        super(MessageType.Acknowledgment, id);
    }
}
