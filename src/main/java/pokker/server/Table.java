package pokker.server;

import java.util.ArrayList;
import java.util.List;

public class Table {
    private final List<Player> players = new ArrayList<>();
    private final int id = 1; //hardcoded atm, but should be generated, maybe from hashCode

    public void broadcast() {
        players.forEach(Player::send);
    }
}
