package pokker.client;

import pokker.lib.Player;

public class BestHand implements Comparable<BestHand> {
    Player player; //whose hand
    String value;

    /*Value has three or two chars in it, depending on the type of the hand.
     First one displays the overall type of it (e.g. high card has 'A', pair has 'B' ...
     2nd char shows  the value of the biggest card in straight or higher member of a full house, or value of a triple.
      Full house and two pairs have also a third char to implicate the lower pair.*/
    BestHand(String string, Player player) {
        this.value = string;
        this.player = player;
    }

    @Override
    public int compareTo(BestHand o) {
        return o.value.compareTo(this.value); //so that better hands would be in front of the list
    }
}