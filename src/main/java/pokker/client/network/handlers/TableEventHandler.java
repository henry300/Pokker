package pokker.client.network.handlers;

import pokker.client.game.TableClient;
import pokker.client.network.ServerConnection;
import pokker.lib.network.messages.MessageContainer;
import pokker.lib.network.messages.MessageHandler;
import pokker.lib.network.messages.TableEventMessage;

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
            case BETTING_ROUND_START:
                table.bettingRoundStart();
                break;
            case ROUND_END:
                table.roundEnd();
                break;
        }
    }
}
