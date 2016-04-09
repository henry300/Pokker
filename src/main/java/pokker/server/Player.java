package pokker.server;

public class Player {
    private final ClientConnection connection;

    Player(ClientConnection connection) {
        this.connection = connection;
    }

    public void send() {
        connection.write();
    }
}
