package pokker.server.network.handlers;

import pokker.lib.network.messages.Message;
import pokker.lib.network.messages.MessageHandler;
import pokker.lib.network.messages.MessageType;
import pokker.server.game.Server;
import pokker.server.game.TableServer;
import pokker.server.network.ClientConnection;

import java.util.List;

/**
 * Handler for when a client asks for the list of tables on the server
 */
public class GetTableListHandler implements MessageHandler<ClientConnection> {
    @Override
    public void handleMessage(ClientConnection connection, Message message) {
        Server server = connection.getServer();

        List<TableServer> tables = server.getTables();
        connection.sendMessage(new Message(MessageType.TableList, tables));
    }
}
