package pokker.lib;

import pokker.lib.messages.Message;
import pokker.lib.messages.MessageHandler;
import pokker.lib.messages.MessageType;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public abstract class Connection {
    private final BlockingQueue<Message> messagesOut = new LinkedBlockingQueue<>();
    private final Socket socket;
    private final Map<MessageType, MessageHandler> messageHandlers;
    private MessageType waitFor;

    public Connection(Socket socket, Map<MessageType, MessageHandler> messageHandlers) {
        this.messageHandlers = messageHandlers;
        this.socket = socket;
    }

    protected void startReadingMessages() {
        new Thread(() -> {
            try (
                    DataInputStream dataIn = new DataInputStream(socket.getInputStream());
            ) {
                while (socket.isConnected()) {
                    try {
                        Message message = receiveMessage(dataIn);
                        handleMessage(message);
                    } catch (IOException e) {
                        System.out.println("Reading message failed: " + e.getMessage());
                        break;
                    }
                }
            } catch (IOException e) {
                System.out.println("Something happened to the input stream from client!");
                close();
            }
        }).start();
    }

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

    private Message receiveMessage(DataInputStream dataIn) throws IOException {
        String data = dataIn.readUTF();
        return Message.parseJsonMessage(data);
    }

    private void sendFromQueue(DataOutputStream dataOut) throws IOException {
        try {
            Message message = messagesOut.take();
            dataOut.writeUTF(message.toJson());
            dataOut.flush();
        } catch (InterruptedException e) {
            System.out.println("Failed taking a message out of queue!");
        }
    }

    public void sendMessage(Message message) {
        messagesOut.add(message);
    }

    private synchronized void handleMessage(Message message) {
        MessageHandler handler = messageHandlers.get(message.getType());

        if (handler != null) {
            handler.handleMessage(this, message);
        }

        if (waitFor == message.getType()) {
            this.notify();
        }
    }

    public synchronized void sendMessageAndWaitForResponseType(Message message, MessageType type) {
        sendMessage(message);
        waitFor = type;
        try {
            this.wait();
        } catch (InterruptedException e) {

        }
    }

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
