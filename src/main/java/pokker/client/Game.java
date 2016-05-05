package pokker.client;

import pokker.lib.messages.Message;
import pokker.lib.messages.MessageType;
import pokker.lib.messages.Request;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Game {
    private ServerConnection connection;
    private List<TableClient> tables;
    private List<TableClient> joinedTables = new ArrayList<>();
    private final String playerName;

    /**
     * Constructs a new Game with given Player name
     * @param playerName
     */
    Game(String playerName) {
        this.playerName = playerName;
    }

    /**
     * Creates a new connection with server
     * @param ip
     * @param port
     * @throws IOException
     */
    public void connect(String ip, int port) throws IOException {
        disconnect();
        connection = new ServerConnection(this, ip, port);
        sendUserData();
    }

    /**
     * Sends user data to the server
     */
    private void sendUserData() {
        connection.sendMessage(new Message(MessageType.UserData, playerName));
    }

    /**
     * Requests list of tables from the server. Server sends back list of talbe objects.
     */
    public void updateTables() {
        connection.sendMessageAndWaitForResponseType(new Request(MessageType.GetTableList), MessageType.TableList);
    }

    /**
     * Joins the user to the table. Adds table to joinedTables list.
     * @param tableId id of the table to join
     * @return retruns false if table is full, otherwise, if successful join, returns true.
     */
    public boolean joinTable(int tableId) {
        updateTables();
        TableClient table = getTableById(tableId);

        if (table.getPlayers().size() >= table.getTableSize()) {
            return false;
        }
        connection.sendMessageAndWaitForResponseType(new Message(MessageType.JoinTable, tableId), MessageType.SuccessfulTableJoin);
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
    TableClient getTableById(int tableId) {
        return getTables().get(tableId);
    }

    /**
     * Overwrites the tables list with provided table list
     * @param tables
     */
    public synchronized void setTables(List<TableClient> tables) {
        this.tables = tables;
    }

    /**
     * Closes the connection
     */
    public void disconnect() {
        if (connection != null) {
            connection.close();
        }
    }
}
