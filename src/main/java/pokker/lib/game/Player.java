package pokker.lib.game;

import com.google.gson.annotations.Expose;

/**
 * Represents a player at a table.
 */
public abstract class Player {
    private final Hand hand = new Hand();

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


    protected Player(String name) {
        this.name = name;
    }

    /**
     * Sets player's cards
     *
     * @param cards
     */
    void setCards(Card[] cards) {
        hand.clear();
        hand.add(cards[0]);
        hand.add(cards[1]);
    }

    /**
     * @return player's hand
     */
    protected Hand getHand() {
        return hand;
    }

    /**
     * Sets player's street bet
     *
     * @param bet
     */
    void setStreetBet(int bet) {
        if (bet > streetBet) {
            money -= (bet - streetBet);
            streetBet = bet;
        }
    }

    protected int getStreetBet() {
        return streetBet;
    }

    void resetStreetBet() {
        streetBet = 0;
    }

    protected String getName() {
        return name;
    }

    void recieveMoney(int money) {
        this.money += money;
    }

    protected int getMoney() {
        return money;
    }

    public abstract int act(int largestBet);
}
