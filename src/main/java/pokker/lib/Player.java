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
    public void setCards(Card[] cards) {
        this.cards[0] = cards[0];
        this.cards[1] = cards[1];
    }

    /**
     * Returns player's cards
     *
     * @return An array of the cards that the player has
     */
    public Card[] getCards() {
        return new Card[]{cards[0], cards[1]};
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

    public Action[] getAllowedCheckActions() {
        return allowedCheckActions;
    }

    public Action[] getAllowedCallActions() {
        return allowedCallActions;
    }

    public void recieveMoney(int money) {
        this.money += money;
    }

    public int getMoney() {
        return money;
    }

    public int act(int largestBet) {
        return -1;
    }
}
