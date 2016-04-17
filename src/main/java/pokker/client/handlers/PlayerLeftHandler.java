package pokker.client.handlers;

import pokker.client.ServerConnection;
import pokker.client.TableClient;
import pokker.lib.Player;
import pokker.lib.messages.Message;
import pokker.lib.messages.MessageHandler;
import pokker.lib.messages.TableMessage;

public class PlayerLeftHandler implements MessageHandler<ServerConnection> {
    @Override
    public void handleMessage(ServerConnection connection, Message message) {
        TableMessage tableMessage = new TableMessage(message);
        Player player = message.messageToObject(Player.class);

        for(TableClient table : connection.getGame().getTables()){
            // TODO: remove the player from the table a bit more intelligently. This is a lazy implementation.
            table.playerLeft(player);
        }
    }
}
