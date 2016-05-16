package pokker.client.gui;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import pokker.lib.game.player.Player;

import java.text.DecimalFormat;
import java.text.NumberFormat;

class Seat extends VBox {
    int seatNr;
    boolean active = false;
    Label nameLabel = new Label();
    Label moneyLabel = new Label("0€");

    //TODO Player info for testing. Later change for player object
    String playerName;
    double money = 0;
    Player player;

    public Seat(int seatNr, int x, int y) {
        this.setSpacing(7);
        this.seatNr = seatNr;
        this.setTranslateX(x);
        this.setTranslateY(y);
        this.getStyleClass().add("unactiveSeat");
        this.setMaxSize(122, 52);
        this.setAlignment(Pos.BASELINE_CENTER);
        nameLabel.setTextFill(Color.WHITE);
        moneyLabel.setTextFill(Color.WHITE);
        this.getChildren().addAll(nameLabel, moneyLabel);
    }

    public void addPlayer(Player player) {
        setMoney(player.getMoney());
        setPlayerName(player.getName());
        this.player = player;
    }

    /**
     * 'Active' means that it is this player's turn
     * @param active boolean true or false
     */
    public void setActive(boolean active) {

        if (active == true) {
            this.getStyleClass().remove("unactiveSeat");
            this.getStyleClass().add("activeSeat");
        } else {
            this.getStyleClass().remove("activeSeat");
            this.getStyleClass().add("unactiveSeat");
        }
        this.active = active;
    }

    public int getSeatNr() {
        return seatNr;
    }

    public void setMoney(double money) {
        NumberFormat nf = new DecimalFormat("##.##");
        moneyLabel.setText(nf.format(money) + "€");
        this.money = money;
    }

    public boolean isActive() {
        return active;
    }

    public String getPlayerName() {
        return playerName;
    }

    public double getMoney() {
        return money;
    }

    public void setPlayerName(String playerName) {
        nameLabel.setText(playerName);
        this.playerName = playerName;
    }
}