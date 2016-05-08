package pokker.client.network.handlers;

import com.google.gson.reflect.TypeToken;
import pokker.client.network.ServerConnection;
import pokker.client.game.TableClient;
import pokker.lib.network.messages.Message;
import pokker.lib.network.messages.MessageHandler;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Handles the message when a list of tables is received
 */
public class TableListHandler implements MessageHandler<ServerConnection> {
    @Override
    public void handleMessage(ServerConnection connection, Message message) {
        Type tablesType = new TypeToken<List<TableClient>>() {
        }.getType();
        List<TableClient> tables = message.bodyToObject(tablesType);
        connection.getGame().setTables(tables);
    }
}
