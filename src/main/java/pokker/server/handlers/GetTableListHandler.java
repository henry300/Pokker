package pokker.server.handlers;

import pokker.lib.messages.Message;
import pokker.server.ClientConnection;
import pokker.server.Server;
import pokker.server.Table;

import java.util.List;

public class GetTableListHandler implements MessageHandler {

    @Override
    public void handleMessage(ClientConnection connection, Message message) {
        Server server = connection.getServer();

        List<Table> tables = server.getTables();
        connection.sendMessage(new Message("tableList", tables));
    }
}
