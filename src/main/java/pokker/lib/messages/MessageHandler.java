package pokker.lib.messages;

import pokker.lib.Connection;

/**
 * Handles a message
 * @param <T> Type that extends Connection
 */
public interface MessageHandler<T extends Connection> {
    void handleMessage(T connection, Message message);
}
