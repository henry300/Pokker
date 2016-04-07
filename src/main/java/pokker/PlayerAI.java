package pokker;

public class PlayerAI extends Player {
    PlayerAI(String name){
        super(name);
    }

    @Override
    public int act(int largestBet){
        System.out.println("It's player " + getName() + " turn (Current bet: " + getStreetBet() + "€ and money left " + getMoney() + "€)");
        if (getStreetBet() < largestBet) {
            System.out.println("He chose to call.");
            setStreetBet(largestBet);
            return largestBet;
        } else {
            System.out.println("He chose to check.");
            return 0;
        }
    }
}
