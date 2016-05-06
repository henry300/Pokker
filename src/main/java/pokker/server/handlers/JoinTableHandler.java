package pokker.server.handlers;

import pokker.lib.messages.Message;
import pokker.lib.messages.MessageHandler;
import pokker.lib.messages.MessageType;
import pokker.server.ClientConnection;

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
