package pokker.server.network.handlers;

import pokker.lib.network.messages.ActMessage;
import pokker.lib.network.messages.MessageContainer;
import pokker.lib.network.messages.MessageHandler;
import pokker.server.game.PlayerClient;
import pokker.server.game.TableServer;
import pokker.server.network.ClientConnection;

public class PlayerActedHandler implements MessageHandler<ClientConnection> {
    @Override
    public void handleMessage(ClientConnection connection, MessageContainer message) {
        ActMessage actMessage = message.bodyToObject(ActMessage.class);

        for (PlayerClient playerClient : connection.getUser().getPlayerClients()) {
            TableServer table = playerClient.getTable();
            if (table.getId() == actMessage.getTableId()) {
                table.playerActed(playerClient, actMessage.getBet());
                break;
            }
        }
    }
}
