package pokker.lib.messages;

public class Request extends Message {
    public Request(MessageType requestType) {
        super(requestType, null);
    }
}
