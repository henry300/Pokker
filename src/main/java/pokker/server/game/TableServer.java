package pokker.server.game;

import com.google.gson.annotations.Expose;
import pokker.lib.game.card.Card;
import pokker.lib.game.card.Deck;
import pokker.lib.game.hands.FullHandFactory;
import pokker.lib.game.hands.Hand;
import pokker.lib.game.player.Player;
import pokker.lib.game.table.BettingRound;
import pokker.lib.game.table.Table;
import pokker.lib.game.table.TableEventType;
import pokker.lib.network.messages.MessageContainer;
import pokker.lib.network.messages.MessageType;
import pokker.lib.network.messages.PlayerJoinedMessage;
import pokker.lib.network.messages.TableMessage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TableServer extends Table<PlayerClient> {
    @Expose
    private final int id;

    private boolean waitingForPlayers = true;
    private final FullHandFactory handFactory = new FullHandFactory();
    private final Deck deck = new Deck();

    TableServer(int tableSize, int bigBlind, int id) {
        super(tableSize, bigBlind);

        this.id = id;
        listen(new TableEventMessager());
    }

    public void broadcast(MessageContainer message) {
        for (PlayerClient player : getPlayers()) {
            player.getUser().getConnection().sendMessage(message);
        }
    }

    @Override
    public void playerJoined(PlayerClient playerThatJoined) {
        getPlayers().add(playerThatJoined);

        for (PlayerClient playerClient : getPlayers()) {
            if (playerClient != playerThatJoined) {
                playerClient.getUser().getConnection().sendMessage(new PlayerJoinedMessage(getId(), playerThatJoined).createContainedMessage());
            }
        }

        if (waitingForPlayers && getPlayers().size() >= 2) {
            roundStart();
        }
    }

    @Override
    public void playerLeft(PlayerClient player) {
        getPlayers().remove(player);
        broadcast(new MessageContainer(MessageType.PlayerLeft, player));
    }

    public int getId() {
        return id;
    }

    private void roundStart() {
        if (getPlayers().size() < 3) {
            waitingForPlayers = true;
            return;
        }
        waitingForPlayers = false;

        deck.shuffle();

        // Deal cards to players
        // TODO: Needs to be redone, because, in real life each player will first get one card
        // and then second, not both at the same time
        // not necessarily
        for (Player player : getPlayers()) {
            player.setCards(new Card[]{deck.draw(), deck.draw()});
        }

        // small blind
        getPlayers().get(1).setStreetBet(getSmallBlind());
        waitForPlayerToAct(getPlayers().get(1));
        dispatchEvent(TableEventType.PLAYER_ACTED);


        // big blind
        getPlayers().get(2).setStreetBet(getBigBlind());
        waitForPlayerToAct(getPlayers().get(2));
        dispatchEvent(TableEventType.PLAYER_ACTED);

        dispatchEvent(TableEventType.ROUND_START);
        // Street starts with player next to the big blind acting first

        bettingRoundStart();
    }

    private void bettingRoundStart() {
        // Deal next card/cards when necessary
        dealCardsToTable(getBettingRound().getAmountOfCardsToDeal());

        dispatchEvent(TableEventType.BETTING_ROUND_START);

        // Assign the first player to act
        waitForNextPlayer();
        askActingPlayerToAct();
    }

    private void bettingRoundEnd() {
        for (Player player : getPlayers()) {
            setPot(getPot() + player.getStreetBet());
            player.resetStreetBet();
            setLargestBet(0);
        }

        dispatchEvent(TableEventType.BETTING_ROUND_END);

        if (getBettingRound() == BettingRound.RIVER) {
            roundEnd();
        } else {
            setNextBettingRound();
            bettingRoundStart();
        }
    }

    private void roundEnd() {
        List<Player> winningPlayers = determineWinningPlayers();
        distributeMoneyToWinningPlayers(winningPlayers);

        Collections.rotate(getPlayers(), -1);

        // kicks cashless people
        for (Player player : getPlayers()) {
            if (player.getMoney() < getBigBlind()) {
                getPlayers().remove(player);
            }
        }

        dispatchEvent(TableEventType.ROUND_END);
        getBoard().clear();
        setBettingRound(BettingRound.PREFLOP);
        roundStart();
    }

    private List<Player> determineWinningPlayers() {
        Hand winningHand = handFactory.createHand(getPlayers().get(0).getHand(), getBoard());
        List<Player> winningPlayers = new ArrayList<>();

        winningPlayers.add(getPlayers().get(0));

        for (int i = 1; i < getPlayers().size(); i++) {
            Player player = getPlayers().get(i);
            Hand hand = handFactory.createHand(player.getHand(), getBoard());

            int compareResult = hand.compareTo(winningHand);

            if (compareResult == 1) {
                winningHand = hand;

                winningPlayers.clear();
                winningPlayers.add(player);
            } else if (compareResult == 0) {
                winningPlayers.add(player);
            }
        }

        return winningPlayers;
    }

    private void distributeMoneyToWinningPlayers(List<Player> winningPlayers) {
        int winningSum = getPot() / winningPlayers.size();

        for (Player winningPlayer : winningPlayers) {
            winningPlayer.recieveMoney(winningSum);
        }
    }

    private void dealCardsToTable(int amountToDeal) {
        for (int i = 0; i < amountToDeal; i++) {
            getBoard().add(deck.draw());
        }
    }

    @Override
    public void playerActed(PlayerClient player, int bet) {
        getActingPlayer().setStreetBet(bet);

        boolean raised = false;
        if (bet > getLargestBet()) {
            raised = true;
            setLastPlayerOfBettingRound(getActingPlayer());
            setLargestBet(bet);
        }

        dispatchEvent(TableEventType.PLAYER_ACTED);

        if (!raised && getActingPlayer() == getLastPlayerOfBettingRound()) {
            bettingRoundEnd();
        } else {
            waitForNextPlayer();
            askActingPlayerToAct();
        }
    }

    private void waitForNextPlayer() {
        waitForPlayerToAct(getPlayers().get((getPlayers().indexOf(getActingPlayer()) + 1) % getPlayers().size()));
    }

    private void askActingPlayerToAct() {
        getActingPlayer().getUser().getConnection().sendMessage(new TableMessage(id).createContainedMessage(MessageType.AskForPlayerAct));
    }
}
