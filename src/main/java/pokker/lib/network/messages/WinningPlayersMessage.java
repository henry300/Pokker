package pokker.lib.network.messages;

import com.google.gson.annotations.Expose;
import pokker.lib.game.player.Player;

import java.util.ArrayList;
import java.util.List;

public class WinningPlayersMessage extends TableMessage {
    @Expose
    private final List<Integer> winningPlayersPos;

    public WinningPlayersMessage(int tableId, List<Integer> winningPlayersPos) {
        super(tableId);

        this.winningPlayersPos = winningPlayersPos;
    }

    public MessageContainer createContainedMessage() {
        return new MessageContainer(MessageType.WinningPlayers, this);
    }

    public List<Integer> getWinningPlayersPos() {
        return winningPlayersPos;
    }
}
