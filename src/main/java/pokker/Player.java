package pokker;

import java.util.Scanner;

public class Player {
    private final Card[] cards;
    private double money;
    private String status;
    private boolean hasReacted;
    private double streetBet;

    Player(){

    }

    double act(double largestBet){
        if(streetBet < largestBet){
            // saab foldida, raiseda, callida
        } else {
            // saab checkida, foldida, betida
        }

        System.out.println("Panusta:");
        Scanner scanner = new Scanner(System.in);

        double bet = scanner.nextDouble();

        money -= bet - streetBet;
        streetBet += bet;
    }

    double getStreetBet(){
        return streetBet;
    }

    void resetStreetBet(){
        streetBet = 0;
    }
}
