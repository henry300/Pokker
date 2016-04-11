package pokker.client.handlers;

import pokker.client.ServerConnection;
import pokker.lib.messages.Message;
import pokker.lib.messages.MessageHandler;

import java.util.HashMap;
import java.util.Map;

public class ClientMessageHandlers {
    protected static Map<String, MessageHandler<ServerConnection>> messageHandlers = new HashMap<>();

    public static void handleMessage(ServerConnection connection, Message message) {
        MessageHandler handler = messageHandlers.get(message.getType());

        if (handler != null) {
            handler.handleMessage(connection, message);
        }
    }

    static {
        messageHandlers.put("tableList", new TableListHandler());
    }

    private ClientMessageHandlers() {

    }
}
