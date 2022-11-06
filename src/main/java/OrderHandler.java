import java.util.ArrayList;

public class OrderHandler {
 // Players start with 20 resources
 // number per resource 1-6
 // there are 6 resource types (6 random numbers are needed)
    private boolean isOrderPossible(int [] playerArray, ArrayList<Integer> orderAsInt){
        for (int i = 0 ; i < 6 ; i++){
            if(orderAsInt.get(i) != 0 && playerArray[i] < orderAsInt.get(i))
                return false;
        }
        return true;
    }

    public boolean isOrderDone(int [] playerArray, ArrayList<Integer> orderAsInt){
        if(!isOrderPossible(playerArray, orderAsInt))
            return false;
        for (int i = 0 ; i < 6 ; i++){
            playerArray[i]= playerArray[i] - orderAsInt.get(i);
        }
        return true;
    }
}
