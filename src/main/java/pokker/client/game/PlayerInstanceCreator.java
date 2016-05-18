package pokker.client.game;

import com.google.gson.InstanceCreator;
import pokker.lib.game.player.Player;

import java.lang.reflect.Type;

public class PlayerInstanceCreator implements InstanceCreator<Player> {
    @Override
    public Player createInstance(Type type) {
        return new PlayerOther("");
    }
}
