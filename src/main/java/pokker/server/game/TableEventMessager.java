package pokker.server.game;

import pokker.lib.game.table.TableEvent;
import pokker.lib.game.table.TableEventListener;
import pokker.lib.network.messages.ActMessage;
import pokker.lib.network.messages.PlayerHandDealtMessage;
import pokker.lib.network.messages.TableEventMessage;
import pokker.lib.network.messages.WaitingForPlayerActMessage;
import pokker.server.network.ClientConnection;

public class TableEventMessager implements TableEventListener<TableServer> {
    @Override
    public void handleTableEvent(TableEvent<TableServer> event) {
        int tableId = event.getTable().getId();
        switch (event.getType()) {
            case ROUND_START:
                for (PlayerClient playerClient : event.getTable().getPlayers()) {
                    ClientConnection connection = playerClient.getUser().getConnection();

                    connection.sendMessage(new PlayerHandDealtMessage(tableId, playerClient.getHand()).createContainedMessage());
                    connection.sendMessage(new TableEventMessage(event.getTable().getId(), event.getType()).createContainedMessage());
                }
                break;
            case PLAYER_ACTED:
                for (PlayerClient playerClient : event.getTable().getPlayers()) {
                    ClientConnection connection = playerClient.getUser().getConnection();

                    connection.sendMessage(new ActMessage(tableId, event.getTable().getActingPlayer().getStreetBet()).createContainedMessage());
                }
                break;
            case WAITING_FOR_PLAYER_TO_ACT:
                int playerPos = event.getTable().getPlayersInRound().indexOf(event.getTable().getActingPlayer());
                event.getTable().broadcast(new WaitingForPlayerActMessage(tableId, playerPos).createContainedMessage());
                break;
            default:
                event.getTable().broadcast(new TableEventMessage(event.getTable().getId(), event.getType()).createContainedMessage());
                break;
        }
    }
}
