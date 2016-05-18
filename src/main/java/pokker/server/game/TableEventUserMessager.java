package pokker.server.game;

import pokker.lib.game.table.TableEvent;
import pokker.lib.game.table.TableEventListener;
import pokker.lib.network.messages.MessageContainer;
import pokker.lib.network.messages.MessageType;
import pokker.lib.network.messages.PlayerHandDealtMessage;
import pokker.server.network.ClientConnection;

public class TableEventUserMessager implements TableEventListener<TableServer> {
    @Override
    public void handleTableEvent(TableEvent<TableServer> event) {
        switch (event.getType()) {
            case ROUND_START:
                int tableId = event.getTable().getId();
                for (PlayerClient playerClient : event.getTable().getPlayers()) {
                    ClientConnection connection = playerClient.getUser().getConnection();

                    connection.sendMessage(MessageContainer.contain(MessageType.PlayerHandDealt, new PlayerHandDealtMessage(tableId, playerClient.getHand())));
                }
        }
    }
}
