package pokker.server;

import pokker.lib.Connection;
import pokker.lib.Player;
import pokker.lib.messages.Message;
import pokker.server.handlers.ServerMessageHandlers;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ClientConnection implements Connection {
    private final BlockingQueue<Message> messagesOut = new LinkedBlockingQueue<>();
    private final Socket socket;
    private pokker.lib.Player player;
    private final Server server;

    public ClientConnection(Server server, Socket socket) throws IOException {
        this.server = server;
        this.socket = socket;
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
                        ServerMessageHandlers.handleMessage(this, message);
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
        String data = dataIn.readUTF();
        return Message.parseJsonMessage(data);
    }

    @Override
    public void sendMessage(Message message) {
        messagesOut.add(message);
    }

    @Override
    public void close() {
        try {
            socket.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Player getPlayer() {
        return player;
    }

    public Server getServer() {
        return server;
    }
}
