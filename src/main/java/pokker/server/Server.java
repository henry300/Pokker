package pokker.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server implements Runnable {
    private final int port;
    private final List<Table> tables = new ArrayList<>();
    private final List<ClientConnection> connections = new ArrayList<>();

    Server(int port) {
        this.port = port;

        tables.add(new Table());
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

    public void broadcast() {
        tables.forEach(Table::broadcast);
    }

    public List<Table> getTables() {
        return tables;
    }

}
