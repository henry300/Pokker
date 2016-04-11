package pokker.client.handlers;

import pokker.client.ServerConnection;
import pokker.lib.messages.Message;

public interface MessageHandler {
    void handleMessage(ServerConnection connection, Message message);
}
