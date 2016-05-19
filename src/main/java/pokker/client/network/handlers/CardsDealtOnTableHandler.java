package pokker.client.network.handlers;

import pokker.client.network.ServerConnection;
import pokker.lib.network.messages.CardsDealtOnTableMessage;
import pokker.lib.network.messages.MessageContainer;
import pokker.lib.network.messages.MessageHandler;

public class CardsDealtOnTableHandler implements MessageHandler<ServerConnection> {
    @Override
    public void handleMessage(ServerConnection connection, MessageContainer message) {
        CardsDealtOnTableMessage tableMessage = message.bodyToObject(CardsDealtOnTableMessage.class);

        connection.getGame().getTableById(tableMessage.getTableId()).cardsDealtToTable(tableMessage.getCards());
    }
}
