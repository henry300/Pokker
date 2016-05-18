package pokker.client.game;

import pokker.client.network.ServerConnection;
import pokker.lib.network.messages.MessageContainer;
import pokker.lib.network.messages.MessageType;
import pokker.lib.network.messages.Request;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Holds the whole gamestate - which server the player is connected to, which tables are open, etc.
 */
public class Game {
    private ServerConnection connection;
    private List<TableClient> tables;
    private List<TableClient> joinedTables = new ArrayList<>();
    private final String playerName;

    /**
     * Constructs a new Game with given Player name
     *
     * @param playerName
     */
    Game(String playerName) {
        this.playerName = playerName;
    }

    /**
     * Creates a new connection with server. If already connected to a server, disconnects from it.
     *
     * @param ip
     * @param port
     * @throws IOException
     */
    void connect(String ip, int port) throws IOException {
        disconnect();
        connection = new ServerConnection(this, ip, port);
        sendUserData();
    }

    /**
     * Sends user data to the server
     */
    private void sendUserData() {
        connection.sendMessage(new MessageContainer(MessageType.UserData, playerName));
    }

    /**
     * Requests list of tables from the server and blocks until a response from server has been received. Then updates
     * the list of tables.
     */
    void updateTables() {
        connection.sendMessageAndWaitForResponseType(new Request(MessageType.GetTableList), MessageType.TableList);
    }

    /**
     * Joins the user to the table. Adds table to joinedTables list.
     *
     * @param tableId id of the table to join
     * @return false if table is full, otherwise, if successful join, returns true.
     */
    boolean joinTable(int tableId) {
        updateTables();
        TableClient table = getTableById(tableId);

        if (table.getPlayers().size() >= table.getTableSize()) {
            return false;
        }
        table.setPlayerMe(new PlayerMe(playerName));
        connection.sendMessageAndWaitForResponseType(new MessageContainer(MessageType.JoinTable, tableId), MessageType.SuccessfulTableJoin);
        joinedTables.add(table);

        return true;
    }

    /**
     * @return list of available tables (TableClient)
     */
    public List<TableClient> getTables() {
        return tables;
    }

    /**
     * @param tableId id of the specific talbe
     * @return specific table from the tables list
     */
    public TableClient getTableById(int tableId) {
        return getTables().get(tableId);
    }

    /**
     * Overwrites the tables list with provided table list
     *
     * @param tables
     */
    public synchronized void setTables(List<TableClient> tables) {
        this.tables = tables;
    }

    /**
     * Closes the connection
     */
    public void disconnect() throws IOException {
        if (connection != null) {
            connection.close();
        }
    }
}
