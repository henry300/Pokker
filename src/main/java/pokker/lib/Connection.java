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
    private Socket socket;
    private final Map<MessageType, MessageHandler> messageHandlers;

    public Connection(Socket socket, Map<MessageType, MessageHandler> messageHandlers) {
        this.messageHandlers = messageHandlers;
        this.socket = socket;
    }

    public Connection(Map<MessageType, MessageHandler> messageHandlers) {
        this.messageHandlers = messageHandlers;
    }

    protected void setSocket(Socket socket) {
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
                        MessageHandler handler = messageHandlers.get(message.getType());

                        if (handler != null) {
                            handler.handleMessage(this, message);
                        }
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
