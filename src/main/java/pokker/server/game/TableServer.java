package pokker.server.game;

import com.google.gson.annotations.Expose;
import pokker.lib.game.table.Table;
import pokker.lib.network.messages.Message;
import pokker.lib.network.messages.MessageType;

public class TableServer extends Table<PlayerClient> {
    @Expose
    private final int id;

    TableServer(int tableSize, int bigBlind, int id) {
        super(tableSize, bigBlind);

        this.id = id;
        listen(new TableEventMessager());
    }

    public void broadcast(Message message) {
        for (PlayerClient player : getPlayers()) {
            player.getUser().getConnection().sendMessage(message);
        }
    }

    @Override
    public void playerJoined(PlayerClient player) {
        broadcast(new Message(MessageType.PlayerJoined, player));
        super.playerJoined(player);
    }

    @Override
    public void playerLeft(PlayerClient player) {
        broadcast(new Message(MessageType.PlayerLeft, player));
        super.playerLeft(player);
    }
}
