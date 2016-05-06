package pokker.server;

import pokker.lib.messages.Message;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

public class Server implements Runnable {
    /**
     * Port that this server listens on
     */
    private final int port;

    /**
     * List of tables available on the server
     */
    private final List<TableServer> tables = new ArrayList<>();

    /**
     * This counter is used to assign id-s to new tables
     */
    private int tableIdCounter = 0;

    /**
     * List of users connected to the server
     */
    private final List<User> users = new ArrayList<>();

    Server(int port) {
        this.port = port;

        //TODO: create new tables dynamically (based on amount of users etc)
        createNewTable(6, 100);
        createNewTable(9, 500);
    }

    /**
     * Creates a new table on the server
     *
     * @param tableSize maximum amount of players that the table can hold
     * @param bigBlind  size of the big blind
     */
    private void createNewTable(int tableSize, int bigBlind) {
        tables.add(new TableServer(tableSize, bigBlind, tableIdCounter++));
    }

    /**
     * This is called when a user connects to the server. This is important since a connection != user. A user is someone
     * who has sent information about themselves to the server - so only after that data has been received can the
     * server acknowledge that there's an actual user on the server (and not just an open connection)
     *
     * @param user
     */
    protected void userConnected(User user) {
        users.add(user);
    }

    /**
     * Runs the server
     */
    @Override
    public void run() {
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

    /**
     * Starts accepting sockets on the ServerSocket
     *
     * @param serverSocket
     * @throws IOException
     */
    private void acceptSockets(ServerSocket serverSocket) throws IOException {
        while (true) {
            Socket clientSocket = serverSocket.accept();
            new ClientConnection(this, clientSocket);
        }
    }

    /**
     * This is called when a user joins a table
     *
     * @param tableId the id of the table that the user joined
     * @param user    the user that joined the table
     */
    public void userJoinTableId(User user, int tableId) {
        TableServer table = tables.get(tableId);
        PlayerClient playerClient = new PlayerClient(user, table);
        table.playerJoined(playerClient);
        user.joinedTableAsClient(playerClient);
    }

    /**
     * Broadcasts a message to all users on the server
     *
     * @param message
     */
    public void broadcast(Message message) {
        for (User user : users) {
            user.getConnection().sendMessage(message);
        }
    }

    /**
     * This is called when a user disconnects from the server
     *
     * @param user
     * @see #userConnected(User)
     */
    public void userDisconnected(User user) {
        for (PlayerClient playerClient : user.getPlayerClients()) {
            playerClient.getTable().playerLeft(playerClient);
        }
    }

    /**
     * @return list of tables available on the server
     */
    public List<TableServer> getTables() {
        return tables;
    }

}
