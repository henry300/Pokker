package pokker.client;

import pokker.client.handlers.TableListHandler;
import pokker.lib.Connection;
import pokker.lib.Player;
import pokker.lib.messages.Message;
import pokker.lib.messages.MessageHandler;
import pokker.lib.messages.MessageType;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ServerConnection extends Connection {
    private PlayerMe playerMe;
    private static final Map<MessageType, MessageHandler> messageHandlers = new HashMap<>();

    static {
        messageHandlers.put(MessageType.TABLELIST, new TableListHandler());
    }

    ServerConnection(){
        super(messageHandlers);
    }

    public void connect(String ip, int port) throws IOException {
        close();

        setSocket(new Socket(ip, port));

        startReadingMessages();
        startSendingMessages();
    }

    public Player getPlayer() {
        return playerMe;
    }
}
