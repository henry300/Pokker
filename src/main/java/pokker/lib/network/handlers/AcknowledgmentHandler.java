package pokker.lib.network.handlers;

import pokker.lib.network.Connection;
import pokker.lib.network.messages.Message;
import pokker.lib.network.messages.MessageHandler;
import pokker.lib.network.messages.MessageState;

public class AcknowledgmentHandler implements MessageHandler<Connection> {
    @Override
    public void handleMessage(Connection connection, Message message) {
        Message messageWaitingForAck = connection.getMessageWaitingForAckById(message.bodyToObject(int.class));

        if (messageWaitingForAck != null) {
            messageWaitingForAck.setState(MessageState.SENT_ACK);
        }
    }
}
