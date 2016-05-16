package pokker.server.game;

import pokker.lib.game.table.TableEvent;
import pokker.lib.game.table.TableEventListener;
import pokker.lib.network.messages.Message;
import pokker.lib.network.messages.MessageType;

public class TableEventMessager implements TableEventListener<TableServer> {
    @Override
    public void handleTableEvent(TableEvent<TableServer> event) {
        event.getTable().broadcast(new Message(MessageType.TableEvent, event));
    }
}
