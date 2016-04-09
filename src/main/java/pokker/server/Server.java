package pokker.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Server implements Runnable {
    private final int port;
    private final Map<Table, List<Player>> tablePlayers = new HashMap<>();

    Server(int port) {
        this.port = port;
    }

    @Override
    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                new Thread(new ClientConnection(clientSocket)).start();
            }
        } catch (IOException e) {
            System.out.println("Could not open socket at port " + port + " !");
        }
    }

    public void broadcast() {
        tablePlayers.keySet().forEach(this::broadcastTable);
    }

    public void broadcastTable(Table table) {
        tablePlayers.get(table).forEach(Player::send);
    }
}
