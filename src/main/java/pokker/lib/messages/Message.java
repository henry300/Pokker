package pokker.lib.messages;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

import java.lang.reflect.Type;

public class Message {
    private final static Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
    @Expose
    private final MessageType type;
    @Expose
    private final String message;

    public Message(MessageType type, String message) {
        this.type = type;
        this.message = message;
    }

    public Message(MessageType type, Object object) {
        this(type, gson.toJson(object));
    }

    public static Message parseJsonMessage(String jsonMessage) {
        return gson.fromJson(jsonMessage, Message.class);
    }

    public <T> T messageToObject(Class<T> classOfT) {
        return gson.fromJson(message, classOfT);
    }

    public <T> T messageToObject(Type typeOfT) {
        return gson.fromJson(message, typeOfT);
    }

    public String toJson() {
        return gson.toJson(this);
    }

    public MessageType getType() {
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
