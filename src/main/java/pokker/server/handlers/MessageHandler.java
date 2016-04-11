package pokker.server.handlers;

import pokker.lib.Connection;
import pokker.lib.messages.Message;
import pokker.server.ClientConnection;

public interface MessageHandler {
    void handleMessage(ClientConnection connection, Message message);
}
