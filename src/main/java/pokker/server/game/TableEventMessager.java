package pokker.server.game;

import pokker.lib.game.table.TableEvent;
import pokker.lib.game.table.TableEventListener;
import pokker.lib.network.messages.ActMessage;
import pokker.lib.network.messages.MessageContainer;
import pokker.lib.network.messages.MessageType;
import pokker.lib.network.messages.PlayerHandDealtMessage;
import pokker.server.network.ClientConnection;

public class TableEventMessager implements TableEventListener<TableServer> {
    @Override
    public void handleTableEvent(TableEvent<TableServer> event) {
        int tableId = event.getTable().getId();
        switch (event.getType()) {
            case ROUND_START:
                for (PlayerClient playerClient : event.getTable().getPlayers()) {
                    ClientConnection connection = playerClient.getUser().getConnection();

                    connection.sendMessage(MessageContainer.contain(MessageType.PlayerHandDealt, new PlayerHandDealtMessage(tableId, playerClient.getHand())));
                }
                break;
            case PLAYER_ACTED:
                for (PlayerClient playerClient : event.getTable().getPlayers()) {
                    if (playerClient != event.getTable().getActingPlayer()) {
                        ClientConnection connection = playerClient.getUser().getConnection();

                        connection.sendMessage(MessageContainer.contain(MessageType.PlayerAct, new ActMessage(tableId, playerClient.getStreetBet())));
                    }
                }
                break;
            default:
                event.getTable().broadcast(new MessageContainer(MessageType.TableEvent, event));
                break;
        }
    }
}
