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
import pokker.lib.network.messages.*;

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
        getPlayersInRound().clear();
        getPlayersInRound().addAll(getPlayers());

        // Deal cards to players
        // TODO: Needs to be redone, because, in real life each player will first get one card
        // and then second, not both at the same time
        // not necessarily
        for (Player player : getPlayersInRound()) {
            player.setCards(new Card[]{deck.draw(), deck.draw()});
        }

        dispatchEvent(TableEventType.ROUND_START);
        // small blind
        getPlayersInRound().get(1).setStreetBet(getSmallBlind());
        waitForPlayerToAct(getPlayersInRound().get(1));
        dispatchEvent(TableEventType.PLAYER_ACTED);


        // big blind
        getPlayersInRound().get(2).setStreetBet(getBigBlind());
        waitForPlayerToAct(getPlayersInRound().get(2));
        dispatchEvent(TableEventType.PLAYER_ACTED);

        setLargestBet(getBigBlind());
        // Street starts with player next to the big blind acting first

        bettingRoundStart(getPlayersInRound().get(2));
    }

    private void bettingRoundStart() {
        bettingRoundStart(getPlayersInRound().get(0));
    }

    private void bettingRoundStart(PlayerClient lastPlayerOfBettingRound) {
        setLastPlayerOfBettingRound(lastPlayerOfBettingRound);
        setActingPlayer(lastPlayerOfBettingRound);
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
        }

        setLargestBet(0);

        dispatchEvent(TableEventType.BETTING_ROUND_END);

        if (getBettingRound() == BettingRound.RIVER) {
            roundEnd();
        } else {
            setNextBettingRound();
            bettingRoundStart();
        }
    }

    private void roundEnd() {
        List<PlayerClient> winningPlayers = determineWinningPlayers();
        List<Integer> winningPlayersPos = new ArrayList<>();

        for (PlayerClient winningPlayer : winningPlayers) {
            winningPlayersPos.add(getPlayers().indexOf(winningPlayer));
        }

        broadcast(new WinningPlayersMessage(getId(), winningPlayersPos).createContainedMessage());
        distributeMoneyToWinningPlayers(winningPlayers);

        Collections.rotate(getPlayers(), -1);

        // kicks cashless people
        for (PlayerClient player : getPlayers()) {
            if (player.getMoney() < getBigBlind()) {
                playerLeft(player);
            }
        }

        dispatchEvent(TableEventType.ROUND_END);
        getBoard().clear();
        setPot(0);
        setBettingRound(BettingRound.PREFLOP);
        roundStart();
    }

    private List<PlayerClient> determineWinningPlayers() {
        Hand winningHand = handFactory.createHand(getPlayersInRound().get(0).getHand(), getBoard());
        List<PlayerClient> winningPlayers = new ArrayList<>();

        winningPlayers.add(getPlayers().get(0));

        for (int i = 1; i < getPlayers().size(); i++) {
            PlayerClient player = getPlayers().get(i);
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

    private void dealCardsToTable(int amountToDeal) {
        for (int i = 0; i < amountToDeal; i++) {
            getBoard().add(deck.draw());
        }

        dispatchEvent(TableEventType.CARDS_DEALT_ON_TABLE);
    }

    @Override
    public void playerActed(PlayerClient player, int bet) {
        if (bet > player.getStreetBet()) {
            player.setMoney(getActingPlayer().getMoney() - bet + player.getStreetBet());
            player.setStreetBet(bet);
        }

        boolean raised = false;
        if (bet > getLargestBet()) {
            raised = true;
            setLastPlayerOfBettingRound(getActingPlayer());
            setLargestBet(bet);
        } else if (bet < getLargestBet()) {
            setActingPlayerIndex(getActingPlayerIndex() - 1);
            getPlayersInRound().remove(player);
        }

        dispatchEvent(TableEventType.PLAYER_ACTED);

        if (getPlayersInRound().size() == 1) {
            roundEnd();
        } else if (!raised && getActingPlayer() == getLastPlayerOfBettingRound()) {
            bettingRoundEnd();
        } else {
            waitForNextPlayer();
            askActingPlayerToAct();
        }
    }

    private PlayerClient getNextPlayerToAct() {
        return getPlayersInRound().get((getActingPlayerIndex() + 1) % getPlayersInRound().size());
    }

    private void waitForNextPlayer() {
        waitForPlayerToAct(getNextPlayerToAct());
    }

    private void askActingPlayerToAct() {
        getActingPlayer().getUser().getConnection().sendMessage(new TableMessage(id).createContainedMessage(MessageType.AskForPlayerAct));
    }
}
