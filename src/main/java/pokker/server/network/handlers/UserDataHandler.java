package pokker.server.network.handlers;

import pokker.lib.network.messages.Message;
import pokker.lib.network.messages.MessageHandler;
import pokker.server.game.User;
import pokker.server.network.ClientConnection;

/**
 * Handler for when a client sends their data
 */
public class UserDataHandler implements MessageHandler<ClientConnection> {
    @Override
    public void handleMessage(ClientConnection connection, Message message) {
        if (connection.getUser() == null) {
            User user = new User(connection, message.getBody());
            connection.setUser(user);
            connection.getServer().userConnected(user);
        }
    }
}
