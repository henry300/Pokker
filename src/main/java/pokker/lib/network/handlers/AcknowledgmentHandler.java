package pokker.lib.network.handlers;

import pokker.lib.network.Connection;
import pokker.lib.network.messages.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class AcknowledgmentHandler implements MessageHandler<Connection> {
    @Override
    public void handleMessage(Connection connection, Message message) {
        Message messageWaitingForAck = connection.getMessageWaitingForAckByHash(message.bodyToObject(Integer.TYPE));

        if(message != null) {
            messageWaitingForAck.setState(MessageState.SENT_ACK);
        }
    }
}
