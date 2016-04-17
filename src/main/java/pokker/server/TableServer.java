package pokker.server;

import com.google.gson.annotations.Expose;
import pokker.lib.Player;
import pokker.lib.Table;
import pokker.lib.messages.Message;
import pokker.lib.messages.MessageType;

public class TableServer extends Table<PlayerClient> {
    @Expose
    private final int id;

    TableServer(int tableSize, int bigBlind, int id) {
        super(tableSize, bigBlind);

        this.id = id;
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
