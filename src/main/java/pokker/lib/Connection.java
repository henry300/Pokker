package pokker.lib;

import pokker.lib.messages.Message;

public interface Connection {
    void sendMessage(Message message);
    void close();
    Player getPlayer();
}
