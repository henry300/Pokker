package pokker.server;

import com.google.gson.annotations.Expose;
import pokker.lib.Table;
import pokker.lib.messages.Message;

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
}
