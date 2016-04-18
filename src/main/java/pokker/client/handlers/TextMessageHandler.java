package pokker.client.handlers;

import pokker.client.ServerConnection;
import pokker.lib.messages.Message;
import pokker.lib.messages.MessageHandler;

public class TextMessageHandler implements MessageHandler<ServerConnection> {
    @Override
    public void handleMessage(ServerConnection connection, Message message) {
        System.out.println(message.getMessage());
    }
}
