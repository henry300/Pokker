package pokker.lib.network.messages;

import pokker.lib.network.Connection;

/**
 * Handles a message
 *
 * @param <T> Type that extends Connection
 */
public interface MessageHandler<T extends Connection> {
    void handleMessage(T connection, MessageContainer message);
}
