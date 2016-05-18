package pokker.server.network.handlers;

import pokker.lib.network.messages.MessageContainer;
import pokker.lib.network.messages.MessageHandler;
import pokker.lib.network.messages.MessageType;
import pokker.server.network.ClientConnection;

/**
 * Handler for when a client wants to join a table
 */
public class JoinTableHandler implements MessageHandler<ClientConnection> {
    @Override
    public void handleMessage(ClientConnection connection, MessageContainer message) {
        Integer id = message.bodyToObject(Integer.class);

        connection.getServer().userJoinTableId(connection.getUser(), id);

        connection.sendMessage(new MessageContainer(MessageType.SuccessfulTableJoin, null));
    }
}
