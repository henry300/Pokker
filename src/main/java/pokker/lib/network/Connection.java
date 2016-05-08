package pokker.lib.network;

import pokker.lib.network.messages.Message;
import pokker.lib.network.messages.MessageHandler;
import pokker.lib.network.messages.MessageType;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Represents a connection to a socket
 */
public abstract class Connection {
    /**
     * Messages that have to be sent out
     */
    private final BlockingQueue<Message> messagesOut = new LinkedBlockingQueue<>();

    /**
     * Map of message handlers that are used when a message is received
     *
     * @see pokker.lib.network.messages.MessageHandler
     */
    private final Map<MessageType, MessageHandler> messageHandlers;

    /**
     * A message type that this connection is supposed to be wait for.
     */
    private MessageType waitFor;

    /**
     * The socket that this connection is connected to
     */
    private final Socket socket;

    public Connection(Socket socket) {
        this.socket = socket;
        this.messageHandlers = loadMessageHandlers();
    }

    /**
     * This method is used to load message handlers when creating the Connection object.
     * <p>
     * This specific implementation just creates an empty Map of message handlers. This should be called by children
     * of this class so that all types of connections use the same implementation of Map.
     *
     * @return
     * @see #Connection(Socket)
     */
    protected Map<MessageType, MessageHandler> loadMessageHandlers() {
        return new HashMap<MessageType, MessageHandler>();
    }

    /**
     * Starts reading messages (and handling) from the socket
     */
    protected void startReadingMessages() {
        new Thread(() -> {
            try (
                    DataInputStream dataIn = new DataInputStream(socket.getInputStream());
            ) {
                while (socket.isConnected()) {
                    Message message = receiveMessage(dataIn);
                    handleMessage(message);
                }
            } catch (SocketException e) {
                close();
                wasClosed();
            } catch (IOException e) {
                close();
                wasClosed();
            }
        }).start();
    }

    /**
     * Sends messages from the `messagesOut` queue
     *
     * @see #messagesOut
     */
    protected void startSendingMessages() {
        new Thread(() -> {
            try (
                    DataOutputStream dataOut = new DataOutputStream(socket.getOutputStream());
            ) {
                while (socket.isConnected()) {
                    try {
                        sendFromQueue(dataOut);
                    } catch (IOException e) {
                        // TODO: try to send again...
                    }
                }
            } catch (IOException e) {
                System.out.println("Something happened to the output stream to the client!");
                close();
            }
        }).start();
    }

    /**
     * Called when the connection is closed.
     */
    public abstract void wasClosed();

    /**
     * Creates a Message object of the data that was received
     *
     * @param dataIn The DataInputStream to read the data from
     * @return a Message object representing the message that was received
     * @throws IOException
     */
    private Message receiveMessage(DataInputStream dataIn) throws IOException {
        String data = dataIn.readUTF();
        return Message.parseJsonMessage(data);
    }

    /**
     * Sends a single message from the queue
     *
     * @param dataOut DataOutPutStream to write the message to
     * @throws IOException
     */
    private void sendFromQueue(DataOutputStream dataOut) throws IOException {
        try {
            Message message = messagesOut.take();
            dataOut.writeUTF(message.toJson());
            dataOut.flush();
        } catch (InterruptedException e) {
            System.out.println("Failed taking a message out of queue!");
        }
    }

    /**
     * Sends a message over the connection.
     *
     * @param message Message object to send
     */
    public void sendMessage(Message message) {
        messagesOut.add(message);
    }

    /**
     * Handles a Message that was received
     *
     * @param message Message object to handle
     */
    private synchronized void handleMessage(Message message) {
        MessageHandler handler = messageHandlers.get(message.getType());

        if (handler != null) {
            handler.handleMessage(this, message);
        }

        if (waitFor == message.getType()) {
            this.notify();
        }
    }

    /**
     * Sends a message and then waits for a response from the other side. This blocks the handling of any other messages.
     *
     * @param message Message to send
     * @param type    Type of message to wait for
     */
    public synchronized void sendMessageAndWaitForResponseType(Message message, MessageType type) {
        sendMessage(message);
        waitFor = type;
        try {
            this.wait();
        } catch (InterruptedException e) {

        }
    }

    /**
     * Closes the connection
     */
    public void close() {
        if (socket != null && !socket.isClosed()) {
            try {
                socket.close();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
