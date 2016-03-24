package pokker;

import java.util.ArrayList;
import java.util.List;

public class Table {
    private final List<Player> players = new ArrayList<>();
    private final int tableSize;
    private final double limit;
    private final Deck deck = new Deck();
    private List<Card> cardsOnTable;
    private double largestBet;
    private double pot;

    Table(int tableSize, double tableLimit){
        this.tableSize = tableSize;
        this.limit = tableLimit;
    }

    private void playerJoin(){

    }

    private void roundStart(){
        // muuda nuppe
        // segada kaardipakk
        // jagada kaardid
        // kõik peavad blindid maksma

        streetStart(players.get(2));

    }

    private void streetStart(Player lastRaised){
        // pane kaart lauda
        int i = 0;
        Player player = null;
        while(player != lastRaised){
            player = players.get(i % players.size());
            double bet = player.act(largestBet);
            // kontrolli üle, et bet oli õige (tee hiljem)

            // player saab checkida, bettida, foldida
            // kui bet == 0, siis check/fold; kui bet > largestBet, siis raise, kui bet == largestBet, siis call

            if(bet > largestBet){
                lastRaised = player;
            }
        }
    }

    private void streetEnd(){
        for (Player player : players) {
            pot += player.getStreetBet();
            player.resetStreetBet();
        }
    }

    private void streetStart(){
        streetStart(players.get(1));
    }

    private void roundEnd(){
        // kontrolli, kes võitsid
        // jaga raha võitjatele (simple)
        // liiguta esimene player viimaseks (nuppude jaoks)
        // kicki mängijad, kellel pole raha
    }
}
