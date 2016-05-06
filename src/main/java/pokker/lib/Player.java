package pokker.lib;

import com.google.gson.annotations.Expose;

/**
 * Represents a player at a table.
 */
public class Player {
    private final Card[] cards = new Card[2];

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

    private Action[] allowedCallActions = {Action.FOLD, Action.RAISE, Action.CALL};
    private Action[] allowedCheckActions = {Action.FOLD, Action.BET, Action.CHECK};

    public Player(String name) {
        this.name = name;
    }

    /**
     * Sets player's cards
     *
     * @param cards
     */
    void setCards(Card[] cards) {
        this.cards[0] = cards[0];
        this.cards[1] = cards[1];
    }

    /**
     * Returns player's cards
     *
     * @return An array of the cards that the player has
     */
    protected Card[] getCards() {
        return new Card[]{cards[0], cards[1]};
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

    protected Action[] getAllowedCheckActions() {
        return allowedCheckActions;
    }

    protected Action[] getAllowedCallActions() {
        return allowedCallActions;
    }

    void recieveMoney(int money) {
        this.money += money;
    }

    protected int getMoney() {
        return money;
    }

    public int act(int largestBet) {
        return -1;
    }
}
