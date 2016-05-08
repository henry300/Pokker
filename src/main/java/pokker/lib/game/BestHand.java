package pokker.lib.game;

public class BestHand implements Comparable<BestHand> {
    private final Player player; //whose hand
    private final Hand hand;


    BestHand(Player player, Hand hand) {
        this.player = player;
        this.hand = hand;
    }


    public Player getPlayer() {
        return player;
    }

    @Override
    public int compareTo(BestHand o) {
        return this.hand.compareTo(o.hand);
    }
}