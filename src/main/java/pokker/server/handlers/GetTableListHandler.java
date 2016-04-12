package pokker.server.handlers;

import pokker.lib.messages.Message;
import pokker.lib.messages.MessageHandler;
import pokker.lib.messages.MessageType;
import pokker.server.ClientConnection;
import pokker.server.Server;
import pokker.server.TableServer;

import java.util.List;

public class GetTableListHandler implements MessageHandler<ClientConnection> {

    @Override
    public void handleMessage(ClientConnection connection, Message message) {
        Server server = connection.getServer();

        List<TableServer> tables = server.getTables();
        connection.sendMessage(new Message(MessageType.TableList, tables));
    }
}
