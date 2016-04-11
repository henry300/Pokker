package pokker.server;

import pokker.lib.messages.Message;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server implements Runnable {
    private final int port;
    private final List<TableServer> tables = new ArrayList<>();
    private int tableIdCounter = 0;
    private final List<ClientConnection> connections = new ArrayList<>();

    Server(int port) {
        this.port = port;

        createNewTable();
    }

    private void createNewTable() {
        tables.add(new TableServer(9, 100, tableIdCounter++));
    }

    @Override
    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            acceptSockets(serverSocket);
        } catch (IOException e) {
            System.out.println("Could not open server socket at port " + port + " !");
        }
    }

    private void acceptSockets(ServerSocket serverSocket) {
        while (true) {
            try {
                Socket clientSocket = serverSocket.accept();
                new ClientConnection(this, clientSocket);
            } catch (IOException e) {
                System.out.println("Something happened with the client!");
            }
        }
    }

    public void broadcast(Message message) {
        for (TableServer table : tables) {
            table.broadcast(message);
        }
    }

    public List<TableServer> getTables() {
        return tables;
    }

}
