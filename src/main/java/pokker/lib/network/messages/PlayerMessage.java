package pokker.lib.network.messages;

import com.google.gson.annotations.Expose;

public class PlayerMessage extends TableMessage {
    @Expose
    private final int playerPos;

    public PlayerMessage(int tableId, int playerPos) {
        super(tableId);

        this.playerPos = playerPos;
    }

    public int getPlayerPos() {
        return playerPos;
    }
}
