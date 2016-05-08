package pokker.server.network;

import pokker.lib.network.Connection;
import pokker.lib.network.messages.MessageHandler;
import pokker.lib.network.messages.MessageType;
import pokker.server.game.Server;
import pokker.server.game.User;
import pokker.server.network.handlers.GetTableListHandler;
import pokker.server.network.handlers.JoinTableHandler;
import pokker.server.network.handlers.UserDataHandler;

import java.net.Socket;
import java.util.Map;

/**
 * Represents a connection to a client
 */
public class ClientConnection extends Connection {
    /**
     * The user using this connection.
     */
    private User user;

    /**
     * The server that this connection is bound to.
     */
    private final Server server;

    public ClientConnection(Server server, Socket socket) {
        super(socket);
        this.server = server;

        startReadingMessages();
        startSendingMessages();
    }

    /**
     * Loads message handlers that will handle messages received from the client.
     *
     * @return a map of message handlers
     * @see Connection
     * @see pokker.lib.network.messages.MessageHandler
     */
    @Override
    protected Map<MessageType, MessageHandler> loadMessageHandlers() {
        Map<MessageType, MessageHandler> messageHandlers = super.loadMessageHandlers();

        messageHandlers.put(MessageType.GetTableList, new GetTableListHandler());
        messageHandlers.put(MessageType.JoinTable, new JoinTableHandler());
        messageHandlers.put(MessageType.UserData, new UserDataHandler());

        return messageHandlers;
    }

    /**
     * @return the server that this connection is bound to
     */
    public Server getServer() {
        return server;
    }

    /**
     * Sets the user of this connection
     *
     * @param user
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * @return the user of this connection
     */
    public User getUser() {
        return user;
    }

    /**
     * Disconnects user from server once the connection is closed
     */
    @Override
    public void wasClosed() {
        server.userDisconnected(user);
    }
}
