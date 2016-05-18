package pokker.lib.network.messages;

import com.google.gson.annotations.Expose;
import pokker.lib.game.table.TableEventType;

public class TableEventMessage extends TableMessage {
    @Expose
    private final TableEventType eventType;

    public TableEventMessage(int tableId, TableEventType eventType) {
        super(tableId);

        this.eventType = eventType;
    }

    public MessageContainer createContainedMessage() {
        return new MessageContainer(MessageType.TableEvent, this);
    }

    public TableEventType getEventType() {
        return eventType;
    }
}
