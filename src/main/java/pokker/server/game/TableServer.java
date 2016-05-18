package pokker.server.game;

import com.google.gson.annotations.Expose;
import pokker.lib.game.table.Table;
import pokker.lib.network.messages.MessageContainer;
import pokker.lib.network.messages.MessageType;
import pokker.lib.network.messages.TableMessage;

public class TableServer extends Table<PlayerClient> {
    @Expose
    private final int id;

    TableServer(int tableSize, int bigBlind, int id) {
        super(tableSize, bigBlind);

        this.id = id;
        listen(new TableEventMessager());
        listen(new TableEventUserMessager());
    }

    public void broadcast(MessageContainer message) {
        for (PlayerClient player : getPlayers()) {
            player.getUser().getConnection().sendMessage(message);
        }
    }

    @Override
    public void playerJoined(PlayerClient player) {
        broadcast(new MessageContainer(MessageType.PlayerJoined, player));
        super.playerJoined(player);
    }

    @Override
    public void playerLeft(PlayerClient player) {
        broadcast(new MessageContainer(MessageType.PlayerLeft, player));
        super.playerLeft(player);
    }

    @Override
    protected void waitForPlayerToAct(PlayerClient player) {
        super.waitForPlayerToAct(player);
        player.getUser().getConnection().sendMessage(MessageContainer.contain(MessageType.AskForPlayerAct, new TableMessage(id)));
    }

    public int getId() {
        return id;
    }
}
