package pokker.client.network.handlers;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import pokker.client.game.PlayerInstanceCreator;
import pokker.client.game.TableClient;
import pokker.client.network.ServerConnection;
import pokker.lib.game.player.Player;
import pokker.lib.game.table.TableEvent;
import pokker.lib.network.messages.MessageContainer;
import pokker.lib.network.messages.MessageHandler;

import java.lang.reflect.Type;

public class TableEventHandler implements MessageHandler<ServerConnection> {
    @Override
    public void handleMessage(ServerConnection connection, MessageContainer message) {
        Type tableEventType = new TypeToken<TableEvent<TableClient>>() {
        }.getType();
        message.setGson(new GsonBuilder().registerTypeAdapter(Player.class, new PlayerInstanceCreator()).create());
        TableEvent<TableClient> tableEvent = message.bodyToObject(tableEventType);
        System.out.println(tableEvent.getType());
    }
}
