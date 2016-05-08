package pokker.server.game;

import pokker.lib.game.Player;
import pokker.server.network.ClientConnection;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a user on the server
 */
public class User {
    /**
     * The connection that the user is using
     */
    private final ClientConnection connection;

    /**
     * Name of the user
     */
    private final String name;

    /**
     * List of the "players" that this user is playing as. Essentially, this can be thought of as a list of tables
     * that the user is connected to, but storing the actual `PlayerClient`s is better.
     *
     * @see Player
     * @see PlayerClient
     */
    private final List<PlayerClient> playerClients = new ArrayList<>();

    public User(ClientConnection connection, String name) {
        this.name = name;
        this.connection = connection;
    }

    /**
     * This is called when the user joins a table
     * TODO: seems like a weird implementation - might have to be changed
     *
     * @param playerClient
     */
    void joinedTableAsClient(PlayerClient playerClient) {
        playerClients.add(playerClient);
    }

    /**
     * This is called when the user leaves a table
     * TODO: seems like a weird implementation - might have to be changed
     *
     * @param playerClient
     */
    void leftTableAsClient(PlayerClient playerClient) {
        playerClients.remove(playerClient);
    }

    /**
     * Returns the list of players that this user is playing as.
     *
     * @return
     * @see #playerClients
     */
    List<PlayerClient> getPlayerClients() {
        return playerClients;
    }

    /**
     * Returns the connection that this user is using
     *
     * @return
     */
    ClientConnection getConnection() {
        return connection;
    }

    /**
     * Returns the name of the user
     *
     * @return
     */
    String getName() {
        return name;
    }
}
