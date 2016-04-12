package pokker.server.handlers;

import pokker.lib.messages.Message;
import pokker.lib.messages.MessageHandler;
import pokker.lib.messages.MessageType;
import pokker.server.ClientConnection;
import pokker.server.PlayerClient;

public class JoinTableHandler implements MessageHandler<ClientConnection> {
    @Override
    public void handleMessage(ClientConnection connection, Message message) {
        Integer id = message.messageToObject(Integer.class);

        connection.getServer().getTables().get(id).playerJoin(new PlayerClient(connection.getUser()));

        connection.sendMessage(new Message(MessageType.SuccessfulTableJoin, null));
    }
}
