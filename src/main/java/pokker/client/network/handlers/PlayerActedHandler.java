package pokker.client.network.handlers;

import pokker.client.game.TableClient;
import pokker.client.network.ServerConnection;
import pokker.lib.network.messages.ActMessage;
import pokker.lib.network.messages.MessageContainer;
import pokker.lib.network.messages.MessageHandler;

public class PlayerActedHandler implements MessageHandler<ServerConnection> {
    @Override
    public void handleMessage(ServerConnection connection, MessageContainer message) {
        ActMessage actMessage = message.bodyToObject(ActMessage.class);

        TableClient table = connection.getGame().getTableById(actMessage.getTableId());

        System.out.println(table.getActingPlayer().getStreetBet());
//        table.playerActed(table.getActingPlayer(), actMessage.getBet());
    }
}
