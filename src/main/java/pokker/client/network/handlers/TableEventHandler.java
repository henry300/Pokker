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
import pokker.lib.network.messages.TableEventMessage;

import java.lang.reflect.Type;

public class TableEventHandler implements MessageHandler<ServerConnection> {
    @Override
    public void handleMessage(ServerConnection connection, MessageContainer message) {
        TableEventMessage eventMessage = message.bodyToObject(TableEventMessage.class);

        TableClient table = connection.getGame().getTableById(eventMessage.getTableId());
        System.out.println(eventMessage.getEventType());
        switch (eventMessage.getEventType()) {
            case ROUND_START:
                table.roundStart();
                break;
            case BETTING_ROUND_END:
                table.bettingRoundEnd();
                break;
        }
    }
}
