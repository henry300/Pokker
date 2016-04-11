package pokker.server;

import pokker.lib.Connection;
import pokker.lib.messages.MessageHandler;
import pokker.lib.messages.MessageType;
import pokker.server.handlers.GetTableListHandler;
import pokker.server.handlers.JoinTableHandler;

import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class ClientConnection extends Connection {
    private final Server server;
    private static final Map<MessageType, MessageHandler> messageHandlers = new HashMap<>();

    static {
        messageHandlers.put(MessageType.GET_TABLELIST, new GetTableListHandler());
        messageHandlers.put(MessageType.JOIN_TABLE, new JoinTableHandler());
    }

    public ClientConnection(Server server, Socket socket) {
        super(socket, messageHandlers);
        this.server = server;

        startReadingMessages();
        startSendingMessages();
    }

    public Server getServer() {
        return server;
    }
}
