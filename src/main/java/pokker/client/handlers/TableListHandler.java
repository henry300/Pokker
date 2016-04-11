package pokker.client.handlers;

import pokker.client.ServerConnection;
import pokker.lib.messages.Message;

public class TableListHandler implements MessageHandler {
    @Override
    public void handleMessage(ServerConnection connection, Message message) {
        System.out.println(message);
    }
}
