package pokker.lib;

/**
 * Created by HansDaniel on 14.04.2016.
 */
public class Hand implements Comparable<Hand> {
    private final HandType handType;
    private final CardValue firstParameter;
    private final CardValue secondParameter;


    Hand(HandType handType, CardValue firstParameter, CardValue secondParameter) {
        this.handType = handType;
        this.firstParameter = firstParameter;
        this.secondParameter = secondParameter;
    }


    @Override
    public int compareTo(Hand o) {
        if (this.handType.compareTo(o.handType) == 0) {
            if (this.firstParameter.compareTo(o.firstParameter) == 0) {
                return this.secondParameter.compareTo(o.secondParameter);
            } else {
                return this.firstParameter.compareTo(o.firstParameter);
            }
        } else {
            return this.handType.compareTo(o.handType);
        }
    }
}
