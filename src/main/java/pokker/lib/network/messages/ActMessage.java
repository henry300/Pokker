package pokker.lib.network.messages;

public class ActMessage extends TableMessage {
    private final int bet;

    public ActMessage(int tableId, int bet) {
        super(tableId);

        this.bet = bet;
    }

    public int getBet() {
        return bet;
    }
}
