package pokker.lib.messages;

import com.google.gson.Gson;

public class Message {
    private final static Gson gson = new Gson();
    private final String type;
    private final String message;

    public Message(String type, String message) {
        this.type = type;
        this.message = message;
    }

    public Message(String type, Object object) {
        this(type, gson.toJson(object));
    }

    public static Message parseJsonMessage(String jsonMessage) {
        return gson.fromJson(jsonMessage, Message.class);
    }

    public String toJson() {
        return gson.toJson(this);
    }

    public String getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "Type: " + getType() + "\n" + getMessage();
    }
}
