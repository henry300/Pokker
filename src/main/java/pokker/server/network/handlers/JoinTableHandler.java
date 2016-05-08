package pokker.server.network.handlers;

import pokker.lib.network.messages.Message;
import pokker.lib.network.messages.MessageHandler;
import pokker.lib.network.messages.MessageType;
import pokker.server.network.ClientConnection;

/**
 * Handler for when a client wants to join a table
 */
public class JoinTableHandler implements MessageHandler<ClientConnection> {
    @Override
    public void handleMessage(ClientConnection connection, Message message) {
        Integer id = message.bodyToObject(Integer.class);

        connection.getServer().userJoinTableId(connection.getUser(), id);

        connection.sendMessage(new Message(MessageType.SuccessfulTableJoin, null));
    }
}
