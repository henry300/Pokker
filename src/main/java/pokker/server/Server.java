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
    private final List<User> users = new ArrayList<>();

    Server(int port) {
        this.port = port;

        createNewTable(6, 100);
        createNewTable(9, 500);
    }

    private void createNewTable(int tableSize, int bigBlind) {
        tables.add(new TableServer(tableSize, bigBlind, tableIdCounter++));
    }

    public void userConnected(User user) {
        users.add(user);
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

    public void playerJoinTableId(PlayerClient player, int tableId) {
        tables.get(tableId).playerJoin(player);
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
