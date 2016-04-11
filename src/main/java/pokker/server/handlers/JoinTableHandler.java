package pokker.server.handlers;

import pokker.lib.messages.Message;
import pokker.lib.messages.MessageHandler;
import pokker.server.ClientConnection;

public class JoinTableHandler implements MessageHandler<ClientConnection> {
    @Override
    public void handleMessage(ClientConnection connection, Message message) {
    }
}
