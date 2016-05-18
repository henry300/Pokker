package pokker.client.network.handlers;

import pokker.client.game.TableClient;
import pokker.client.network.ServerConnection;
import pokker.lib.network.messages.MessageContainer;
import pokker.lib.network.messages.MessageHandler;
import pokker.lib.network.messages.WaitingForPlayerActMessage;

public class WaitingForPlayerActHandler implements MessageHandler<ServerConnection> {
    @Override
    public void handleMessage(ServerConnection connection, MessageContainer message) {
        WaitingForPlayerActMessage waitMessage = message.bodyToObject(WaitingForPlayerActMessage.class);

        TableClient table = connection.getGame().getTableById(waitMessage.getTableId());
        table.waitForPlayerToAct(table.getPlayers().get(waitMessage.getPlayerPos()));
    }
}
