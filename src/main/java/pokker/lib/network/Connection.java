package pokker.lib.network;

import pokker.lib.network.handlers.AcknowledgmentHandler;
import pokker.lib.network.messages.*;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    private final Map<Integer, Message> sentWaitingForAck = new HashMap<>();

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

    private int sentId = 0;

    public Connection(Socket socket) {
        this.socket = socket;
        this.messageHandlers = loadMessageHandlers();

        startReadingMessages();
        startSendingMessages();
        startClearingUnackedMessages();
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
        Map<MessageType, MessageHandler> messageHandlers = new HashMap<>();

        messageHandlers.put(MessageType.Acknowledgment, new AcknowledgmentHandler());

        return messageHandlers;
    }

    /**
     * Starts reading messages (and handling) from the socket
     */
    private void startReadingMessages() {
        new Thread(() -> {
            try {
                try (
                        DataInputStream dataIn = new DataInputStream(socket.getInputStream());
                ) {
                    while (socket.isConnected()) {
                        Message message = receiveMessage(dataIn);

                        if (message.getType() != MessageType.Acknowledgment) {
                            // send acknowledgment that message was received
                            sendMessage(new Acknowledgment(message.getId()));
                        }

                        handleMessage(message);
                    }
                } finally {
                    close();
                    wasClosed();
                }
            } catch (IOException e) {
                Thread.currentThread().interrupt();
                return;
            }

        }).start();
    }

    /**
     * Sends messages from the `messagesOut` queue
     *
     * @see #messagesOut
     */
    private void startSendingMessages() {
        new Thread(() -> {
            try {
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
                } finally {
                    close();
                }
            } catch (IOException e) {
                Thread.currentThread().interrupt();
                return;
            }

        }).start();
    }

    /**
     * Waits for sent messages to be acked for 1min.
     */
    private void startClearingUnackedMessages() {
        new Thread(() -> {
            while (socket.isConnected()) {
                // TODO: resend message once
                List<Integer> toBeRemoved = new ArrayList<>();
                sentWaitingForAck.values().stream().filter(message -> message.getCreatedAt().plusMinutes(1).compareTo(LocalDateTime.now()) == -1).forEach(message -> {
                    toBeRemoved.add(message.getId());
                });

                toBeRemoved.forEach(sentWaitingForAck::remove);

                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
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

        Message message = Message.parseJsonMessage(data);
        message.setState(MessageState.RECEIVED);

        return message;
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
            sentWaitingForAck.put(message.getId(), message);
            dataOut.writeUTF(message.toJson());
            dataOut.flush();

            message.setState(MessageState.SENT);
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
        message.setState(MessageState.TO_BE_SENT);
        message.setId(sentId++);
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

    public Message getMessageWaitingForAckById(int id) {
        return sentWaitingForAck.remove(id);
    }

    /**
     * Closes the connection
     */
    public void close() throws IOException {
        if (socket != null && !socket.isClosed()) {
            socket.close();
        }
    }
}
