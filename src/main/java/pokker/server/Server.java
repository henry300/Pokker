package pokker.server;

import pokker.lib.messages.Message;
import pokker.lib.messages.MessageType;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

public class Server implements Runnable {
    private final int port;
    private final List<TableServer> tables = new ArrayList<>();
    private int tableIdCounter = 0;
    private final List<User> users = new ArrayList<>();

    Server(int port) {
        this.port = port;

        createNewTable(4, 100);
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
        startClock();
        ServerSocket serverSocket = null;
        try (ServerSocket socket = new ServerSocket(port)) {
            serverSocket = socket;
            acceptSockets(serverSocket);
        } catch (SocketException e) {
            if (serverSocket != null && serverSocket.isClosed()) {
                // socket was closed somehow, restarting server
                run();
            } else {
                System.out.println("Could not open server socket at port " + port + " !");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void acceptSockets(ServerSocket serverSocket) throws IOException {
        while (true) {
            Socket clientSocket = serverSocket.accept();
            new ClientConnection(this, clientSocket);
        }
    }

    public void userJoinTableId(User user, int tableId) {
        TableServer table = tables.get(tableId);
        PlayerClient playerClient = new PlayerClient(user, table);
        table.playerJoined(playerClient);
        user.joinedTableAsClient(playerClient);
    }

    public void startClock() {
        new Thread(() -> {
            while(true) {
                for (TableServer table : tables) {
                    if (!table.isGameActive() && table.getPlayers().size() == table.getTableSize()) {
                        table.broadcast(new Message(MessageType.TextMessage, "Game Starts!"));
                        table.gameStart();
                        table.setGameActive(true);
                    }
                }
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void broadcast(Message message) {
        for (TableServer table : tables) {
            table.broadcast(message);
        }
    }

    public void userDisconnected(User user) {
        for (PlayerClient playerClient : user.getPlayerClients()) {
            playerClient.getTable().playerLeft(playerClient);
        }
    }

    public List<TableServer> getTables() {
        return tables;
    }

}
