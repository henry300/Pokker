package pokker.server;

import java.util.ArrayList;
import java.util.List;

public class User {
    private final ClientConnection connection;
    private final String name;
    private final List<PlayerClient> playerClients = new ArrayList<>();

    public User(ClientConnection connection, String name) {
        this.name = name;
        this.connection = connection;
    }

    void joinedTableAsClient(PlayerClient playerClient) {
        playerClients.add(playerClient);
    }

    void leftTableAsClient(PlayerClient playerClient) {
        playerClients.remove(playerClient);
    }

    List<PlayerClient> getPlayerClients() {
        return playerClients;
    }

    ClientConnection getConnection() {
        return connection;
    }

    String getName() {
        return name;
    }
}
