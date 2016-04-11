package pokker.server.handlers;

import pokker.lib.messages.Message;
import pokker.server.ClientConnection;

import java.util.HashMap;
import java.util.Map;

public final class ServerMessageHandlers {
    protected static Map<String, MessageHandler> messageHandlers = new HashMap<>();

    public static void handleMessage(ClientConnection connection, Message message) {
        MessageHandler handler = messageHandlers.get(message.getType());

        if (handler != null) {
            handler.handleMessage(connection, message);
        }
    }
    static {
        messageHandlers.put("getTableList", new GetTableListHandler());
        messageHandlers.put("joinTable", new JoinTableHandler());
    }

    private ServerMessageHandlers() {

    }
}
