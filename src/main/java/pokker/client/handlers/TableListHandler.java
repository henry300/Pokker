package pokker.client.handlers;

import com.google.gson.reflect.TypeToken;
import pokker.client.ServerConnection;
import pokker.client.TableClient;
import pokker.lib.messages.Message;
import pokker.lib.messages.MessageHandler;

import java.lang.reflect.Type;
import java.util.List;

public class TableListHandler implements MessageHandler<ServerConnection> {
    @Override
    public void handleMessage(ServerConnection connection, Message message) {
        Type tablesType = new TypeToken<List<TableClient>>() {
        }.getType();
        List<TableClient> tables = message.messageToObject(tablesType);
        connection.getGame().setTables(tables);
    }
}
