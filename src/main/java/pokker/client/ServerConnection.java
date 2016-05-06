package pokker.client;

import pokker.client.handlers.TableListHandler;
import pokker.lib.Connection;
import pokker.lib.messages.MessageHandler;
import pokker.lib.messages.MessageType;

import java.io.IOException;
import java.net.Socket;
import java.util.Map;

/**
 * Represents a connection to the server.
 */
public class ServerConnection extends Connection {
    /**
     * Holds the game that this connection is bound to.
     *
     * @see pokker.client.Game
     */
    private final Game game;

    ServerConnection(Game game, String ip, int port) throws IOException {
        super(new Socket(ip, port));

        this.game = game;
        startReadingMessages();
        startSendingMessages();
    }

    /**
     * @return the game that this connection is bound to
     */
    public Game getGame() {
        return game;
    }

    /**
     * Loads message handlers that will handle messages received from the server.
     *
     * @return a map of message handlers
     * @see pokker.lib.Connection
     * @see pokker.lib.messages.MessageHandler
     */
    @Override
    protected Map<MessageType, MessageHandler> loadMessageHandlers() {
        Map<MessageType, MessageHandler> messageHandlers = super.loadMessageHandlers();

        messageHandlers.put(MessageType.TableList, new TableListHandler());

        return messageHandlers;
    }

    /**
     * Does nothing when the connection is closed.
     * TODO: maybe retry connection or notify another object to create another connection
     */
    @Override
    public void wasClosed() {

    }
}
