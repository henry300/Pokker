package pokker.server;

public class User {
    private final ClientConnection connection;
    private final String name;

    public User(ClientConnection connection, String name) {
        this.name = name;
        this.connection = connection;
    }

    ClientConnection getConnection() {
        return connection;
    }

    String getName() {
        return name;
    }
}
