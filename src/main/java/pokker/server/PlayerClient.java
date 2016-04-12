package pokker.server;

import pokker.lib.Player;

public class PlayerClient extends Player {
    private final User user;

    public PlayerClient(User user) {
        super(user.getName());
        this.user = user;
    }

    User getUser() {
        return user;
    }
}
