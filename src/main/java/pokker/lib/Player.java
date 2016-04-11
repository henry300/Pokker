package pokker.lib;

import pokker.client.Action;
import pokker.client.Card;

public class Player {
    private final Card[] cards = new Card[2];
    private int money;
    private int hasReacted;
    private int streetBet;  // How much the player has bet on this street.
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
     * @return
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

    public int getHasReacted() {
        return hasReacted;
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
