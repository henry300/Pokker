package pokker.lib.network.messages;

import com.google.gson.annotations.Expose;
import pokker.lib.game.player.Player;

public class PlayerJoinedMessage extends TableMessage {
    @Expose
    private final Player player;

    public PlayerJoinedMessage(int tableId, Player player) {
        super(tableId);
        this.player = player;
    }

    public MessageContainer createContainedMessage() {
        return super.createContainedMessage(MessageType.PlayerJoined);
    }

    public Player getPlayer() {
        return player;
    }
}
