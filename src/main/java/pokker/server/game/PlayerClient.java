package pokker.server.game;

import pokker.lib.game.player.Player;

public class PlayerClient extends Player {
    private final User user;
    private final TableServer table;

    public PlayerClient(User user, TableServer table) {
        super(user.getName());
        this.table = table;
        this.user = user;
    }

    User getUser() {
        return user;
    }

    public TableServer getTable() {
        return table;
    }
}
