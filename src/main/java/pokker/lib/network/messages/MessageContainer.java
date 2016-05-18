package pokker.lib.network.messages;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

import java.lang.reflect.Type;
import java.time.LocalDateTime;

/**
 * Represents a message, to be used to send information between the server and the client
 **/
public class MessageContainer {
    private final static Gson staticGson = new GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .create();

    private MessageState state;
    private final LocalDateTime createdAt = LocalDateTime.now();
    private Gson gson = staticGson;

    @Expose
    private final MessageType type;
    @Expose
    private final String body;
    @Expose
    private int id;

    /**
     * Create a body with just a string as the message body
     *
     * @param type
     * @param body
     */
    public MessageContainer(MessageType type, String body) {
        this.type = type;
        this.body = body;
    }

    /**
     * Create a message that makes a JSON-representation of an object and uses that as the message body
     *
     * @param type
     * @param object
     */
    public MessageContainer(MessageType type, Object object) {
        this(type, staticGson.toJson(object));
    }

    public MessageContainer(MessageType type) {
        this(type, null);
    }

    public MessageContainer() {
        this.type = null;
        this.body = null;
        this.gson = staticGson;
    }

    /**
     * Parses a JSON string and turns it into a Message object
     *
     * @param jsonStr JSON string
     * @return Message object
     */
    public static MessageContainer parseJsonMessage(String jsonStr) {
        return staticGson.fromJson(jsonStr, MessageContainer.class);
    }

    /**
     * Turns the body of the message to an object
     *
     * @param <T>      Type of the object
     * @param classOfT Class of the object
     * @return object of the type `<T>`
     * @see com.google.gson.Gson#fromJson(String, Class)
     */
    public <T> T bodyToObject(Class<T> classOfT) {
        return gson.fromJson(body, classOfT);
    }

    /**
     * Turns the body of the message to an object. Usually used for Lists and such that use generic types
     *
     * @param <T>     Type of the object
     * @param typeOfT Type of the type
     * @return
     * @see com.google.gson.Gson#fromJson(String, Type)
     */
    public <T> T bodyToObject(Type typeOfT) {
        return gson.fromJson(body, typeOfT);
    }

    public MessageContainer setGson(Gson gson) {
        this.gson = gson;
        return this;
    }

    /**
     * Turns the whole message into a JSON-string
     *
     * @return JSON string
     */
    public String toJson() {
        return gson.toJson(this);
    }

    /**
     * @return type of the message
     */
    public MessageType getType() {
        return type;
    }

    /**
     * @return string-representation of the message body
     */
    public String getBody() {
        return body;
    }

    public MessageState getState() {
        return state;
    }

    public void setState(MessageState state) {
        this.state = state;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Type: " + getType() + "\n" + getBody();
    }
}
