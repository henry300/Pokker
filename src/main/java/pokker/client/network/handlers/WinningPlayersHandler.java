package pokker.client.network.handlers;

import pokker.client.game.TableClient;
import pokker.client.network.ServerConnection;
import pokker.lib.game.player.Player;
import pokker.lib.network.messages.MessageContainer;
import pokker.lib.network.messages.MessageHandler;
import pokker.lib.network.messages.WinningPlayersMessage;

import java.util.ArrayList;
import java.util.List;

public class WinningPlayersHandler implements MessageHandler<ServerConnection> {
    @Override
    public void handleMessage(ServerConnection connection, MessageContainer message) {
        WinningPlayersMessage winningPlayersMessage = message.bodyToObject(WinningPlayersMessage.class);

        List<Player> winningPlayers = new ArrayList<>();
        TableClient table = connection.getGame().getTableById(winningPlayersMessage.getTableId());
        List<Player> tablePlayers = table.getPlayers();

        for (Integer integer : winningPlayersMessage.getWinningPlayersPos()) {
            winningPlayers.add(tablePlayers.get(integer));
        }

        table.distributeMoneyToWinningPlayers(winningPlayers);
    }
}
