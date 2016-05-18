package pokker.client.network.handlers;

import pokker.client.game.TableClient;
import pokker.client.network.ServerConnection;
import pokker.lib.game.player.Player;
import pokker.lib.network.messages.MessageContainer;
import pokker.lib.network.messages.MessageHandler;

/**
 * Handles the message when a player leaves from a table.
 */
public class PlayerLeftHandler implements MessageHandler<ServerConnection> {
    @Override
    public void handleMessage(ServerConnection connection, MessageContainer message) {
        Player player = message.bodyToObject(Player.class);

        for (TableClient table : connection.getGame().getTables()) {
            // TODO: remove the player from the table a bit more intelligently. This is a lazy implementation.
            table.playerLeft(player);
        }
    }
}
