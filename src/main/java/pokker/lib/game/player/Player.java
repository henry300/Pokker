package pokker.lib.game.player;

import com.google.gson.annotations.Expose;
import pokker.lib.game.card.Card;

/**
 * Represents a player at a table.
 */
public class Player {
    private final PlayerHand hand = new PlayerHand();

    /**
     * How much money the player has with him on the table.
     */
    @Expose
    private int money;

    /**
     * How much the player has bet on this street
     */
    @Expose
    private int streetBet;

    /**
     * Name of the player
     */
    @Expose
    private final String name;

    public Player(String name) {
        this.name = name;
    }

    /**
     * Sets player's cards
     *
     * @param cards
     */
    public void setCards(Card[] cards) {
        hand.clear();
        hand.add(cards[0]);
        hand.add(cards[1]);
    }

    public void setHand(PlayerHand hand) {
        this.hand.clear();

        hand.getCards().forEach(this.hand::add);
    }

    /**
     * @return player's hand
     */
    public PlayerHand getHand() {
        return hand;
    }

    /**
     * Sets player's street bet
     *
     * @param bet
     */
    public void setStreetBet(int bet) {
        if (bet > streetBet) {
            money -= (bet - streetBet);
            streetBet = bet;
        }
    }

    public int getStreetBet() {
        return streetBet;
    }

    public void resetStreetBet() {
        streetBet = 0;
    }

    public String getName() {
        return name;
    }

    public void recieveMoney(int money) {
        this.money += money;
    }

    public int getMoney() {
        return money;
    }
}
