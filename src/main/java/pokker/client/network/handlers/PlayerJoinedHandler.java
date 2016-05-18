package pokker.client.network.handlers;

import com.google.gson.GsonBuilder;
import pokker.client.game.PlayerInstanceCreator;
import pokker.client.game.TableClient;
import pokker.client.network.ServerConnection;
import pokker.lib.game.player.Player;
import pokker.lib.network.messages.MessageContainer;
import pokker.lib.network.messages.MessageHandler;
import pokker.lib.network.messages.PlayerJoinedMessage;

public class PlayerJoinedHandler implements MessageHandler<ServerConnection> {
    @Override
    public void handleMessage(ServerConnection connection, MessageContainer message) {
        message.setGson(new GsonBuilder().registerTypeAdapter(Player.class, new PlayerInstanceCreator()).create());

        System.out.println("Player joined!");
        PlayerJoinedMessage joinMessage = message.bodyToObject(PlayerJoinedMessage.class);
        TableClient table = connection.getGame().getTableById(joinMessage.getTableId());

        table.playerJoined(joinMessage.getPlayer());
    }
}
