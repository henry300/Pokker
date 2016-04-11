package pokker.client;

import pokker.client.handlers.ClientMessageHandlers;
import pokker.lib.Connection;
import pokker.lib.Player;
import pokker.lib.messages.Message;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ServerConnection implements Connection {
    private final BlockingQueue<Message> messagesOut = new LinkedBlockingQueue<>();
    private Socket socket;
    private PlayerMe playerMe;

    public void connect(String ip, int port) throws IOException {
        close();

        socket = new Socket(ip, port);
        startReadingMessages();
        startSendingMessages();
    }

    private void startReadingMessages() {
        new Thread(() -> {
            try (
                    DataInputStream dataIn = new DataInputStream(socket.getInputStream());
            ) {
                while (socket.isConnected()) {
                    try {
                        Message message = receiveMessage(dataIn);
                        ClientMessageHandlers.handleMessage(this, message);
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

    private void startSendingMessages() {
        new Thread(() -> {
            try (
                    DataOutputStream dataOut = new DataOutputStream(socket.getOutputStream());
            ) {
                while (socket.isConnected()) {
                    try {
                        sendFromQueue(dataOut);
                    } catch (IOException e) {
                        // TODO: try to send again...
                        System.out.println(e.getMessage());
                    }
                }
            } catch (IOException e) {
                System.out.println("Something happened to the output stream to the client!");
                close();
            }
        }).start();
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

    private Message receiveMessage(DataInputStream dataIn) throws IOException {
        String message = dataIn.readUTF();

        return Message.parseJsonMessage(message);
    }

    @Override
    public void sendMessage(Message message) {
        messagesOut.add(message);
    }

    @Override
    public void close() {
        if (socket != null) {
            try {
                socket.close();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @Override
    public Player getPlayer() {
        return playerMe;
    }
}
