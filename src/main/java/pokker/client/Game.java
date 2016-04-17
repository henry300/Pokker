package pokker.client;

import pokker.lib.messages.Message;
import pokker.lib.messages.MessageType;
import pokker.lib.messages.Request;

import java.io.IOException;
import java.util.List;

public class Game {
    private ServerConnection connection;
    private List<TableClient> tables;
    private List<TableClient> joinedTables;
    private final String playerName;

    Game(String playerName) {
        this.playerName = playerName;
    }

    public void connect(String ip, int port) throws IOException {
        disconnect();
        connection = new ServerConnection(this, ip, port);
        sendUserData();
    }

    private void sendUserData() {
        connection.sendMessage(new Message(MessageType.UserData, playerName));
    }

    public void updateTables() {
        connection.sendMessageAndWaitForResponseType(new Request(MessageType.GetTableList), MessageType.TableList);
    }

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

    public List<TableClient> getTables() {
        return tables;
    }

    TableClient getTableById(int tableId) {
        return getTables().get(tableId);
    }


    public synchronized void setTables(List<TableClient> tables) {
        this.tables = tables;
    }

    public void disconnect() {
        if (connection != null) {
            connection.close();
        }
    }
}
