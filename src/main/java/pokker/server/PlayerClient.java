package pokker.server;

import pokker.lib.Player;

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

    TableServer getTable() {
        return table;
    }
}
