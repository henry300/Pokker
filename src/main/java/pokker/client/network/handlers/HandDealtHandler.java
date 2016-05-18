package pokker.client.network.handlers;

import pokker.client.game.TableClient;
import pokker.client.network.ServerConnection;
import pokker.lib.network.messages.MessageContainer;
import pokker.lib.network.messages.MessageHandler;
import pokker.lib.network.messages.PlayerHandDealtMessage;

public class HandDealtHandler implements MessageHandler<ServerConnection> {
    @Override
    public void handleMessage(ServerConnection connection, MessageContainer message) {
        PlayerHandDealtMessage handMessage = message.bodyToObject(PlayerHandDealtMessage.class);

        TableClient table = connection.getGame().getTableById(handMessage.getTableId());

        table.getPlayerMe().setHand(handMessage.getPlayerHand());
    }
}
