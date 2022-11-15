import java.util.*;

public class Players {
    Vector<Integer> player1 = new Vector<Integer>(6);
    Vector<Integer> player2 = new Vector<Integer>(6);
    public Players() {
        Resources resObj = new Resources();
        System.out.println("Giving players resources...");
        resObj.getPlayerResources(player1);
        System.out.println();
        resObj.getPlayerResources(player2);
        System.out.println();
    }
}
