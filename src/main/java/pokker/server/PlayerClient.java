package pokker.server;

import pokker.lib.Player;

public class PlayerClient extends Player {
    private final ClientConnection connection;

    PlayerClient(ClientConnection connection, String name) {
        super(name);
        this.connection = connection;
    }

    ClientConnection getConnection() {
        return connection;
    }
}
