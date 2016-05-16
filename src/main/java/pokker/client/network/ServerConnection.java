package pokker.client.network;

import pokker.client.game.Game;
import pokker.client.network.handlers.TableEventHandler;
import pokker.client.network.handlers.TableListHandler;
import pokker.lib.network.Connection;
import pokker.lib.network.messages.MessageHandler;
import pokker.lib.network.messages.MessageType;

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
     * @see Game
     */
    private final Game game;

    public ServerConnection(Game game, String ip, int port) throws IOException {
        super(new Socket(ip, port));

        this.game = game;
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
     * @see Connection
     * @see pokker.lib.network.messages.MessageHandler
     */
    @Override
    protected Map<MessageType, MessageHandler> loadMessageHandlers() {
        Map<MessageType, MessageHandler> messageHandlers = super.loadMessageHandlers();

        messageHandlers.put(MessageType.TableList, new TableListHandler());
        messageHandlers.put(MessageType.TableEvent, new TableEventHandler());

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
