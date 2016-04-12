package pokker.server;

import pokker.lib.Connection;
import pokker.lib.messages.MessageHandler;
import pokker.lib.messages.MessageType;
import pokker.server.handlers.GetTableListHandler;
import pokker.server.handlers.JoinTableHandler;
import pokker.server.handlers.UserDataHandler;

import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class ClientConnection extends Connection {
    private User user;
    private final Server server;
    private static final Map<MessageType, MessageHandler> messageHandlers = new HashMap<>();

    static {
        messageHandlers.put(MessageType.GetTableList, new GetTableListHandler());
        messageHandlers.put(MessageType.JoinTable, new JoinTableHandler());
        messageHandlers.put(MessageType.UserData, new UserDataHandler());
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

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
