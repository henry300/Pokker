package pokker.server.handlers;

import pokker.lib.messages.Message;
import pokker.lib.messages.MessageHandler;
import pokker.server.ClientConnection;
import pokker.server.User;

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
