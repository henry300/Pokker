package pokker.lib.network.messages;

import com.google.gson.annotations.Expose;
import pokker.lib.game.player.PlayerHand;

public class PlayerHandDealtMessage extends TableMessage {
    @Expose
    private final PlayerHand playerHand;

    public PlayerHandDealtMessage(int tableId, PlayerHand hand) {
        super(tableId);
        this.playerHand = hand;
    }

    public PlayerHand getPlayerHand() {
        return playerHand;
    }

    public MessageContainer createContainedMessage() {
        return super.createContainedMessage(MessageType.PlayerHandDealt);
    }
}
