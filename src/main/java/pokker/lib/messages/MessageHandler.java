package pokker.lib.messages;

import pokker.lib.Connection;

public interface MessageHandler<T extends Connection> {
    void handleMessage(T connection, Message message);
}
