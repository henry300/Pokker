package pokker.client.network.handlers;

import pokker.client.game.TableClient;
import pokker.client.network.ServerConnection;
import pokker.lib.network.messages.ActMessage;
import pokker.lib.network.messages.MessageContainer;
import pokker.lib.network.messages.MessageHandler;
import pokker.lib.network.messages.TableMessage;

public class AskedToActHandler implements MessageHandler<ServerConnection> {
    @Override
    public void handleMessage(ServerConnection connection, MessageContainer message) {
        TableMessage tableMessage = message.bodyToObject(TableMessage.class);
        TableClient table = connection.getGame().getTableById(tableMessage.getTableId());

        int bet = table.getPlayerMe().act(table.getLargestBet());

        connection.sendMessage(new ActMessage(table.getId(), bet).createContainedMessage());
    }
}
