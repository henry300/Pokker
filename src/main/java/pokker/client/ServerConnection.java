package pokker.client;

import pokker.client.handlers.TableListHandler;
import pokker.client.handlers.TextMessageHandler;
import pokker.lib.Connection;
import pokker.lib.messages.MessageHandler;
import pokker.lib.messages.MessageType;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class ServerConnection extends Connection {
    private final Game game;
    private static final Map<MessageType, MessageHandler> messageHandlers = new HashMap<>();

    static {
        messageHandlers.put(MessageType.TableList, new TableListHandler());
        messageHandlers.put(MessageType.TextMessage, new TextMessageHandler());
    }

    ServerConnection(Game game, String ip, int port) throws IOException {
        super(new Socket(ip, port), messageHandlers);

        this.game = game;
        startReadingMessages();
        startSendingMessages();
    }

    public Game getGame() {
        return game;
    }

    @Override
    public void wasClosed() {

    }
}
