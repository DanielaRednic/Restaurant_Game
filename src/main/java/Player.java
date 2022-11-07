import java.util.ArrayList;

public class Player {
    public int[] resources = {0, 0, 0, 0, 0, 0};
    public int score;

    public Player() {
        Resources resObj = new Resources();
        this.resources = resObj.getPlayerResources(resources);
        this.score=0;
    }

    private int[] isOrderPossibleHelper(ArrayList<Integer> orderAsInt){
        int[] k ={0,0,0,0,0,0};
        for (int i = 0 ; i < 6 ; i++){
            k[i]=this.resources[i]-orderAsInt.get(i);
        }
        return k;
    }
    private int isOrderPossible(ArrayList<Integer> orderAsInt) {
        int j=0;
        int k[]= isOrderPossibleHelper(orderAsInt);
        for(int i=0;i<6;i++){
            if(k[i]<0){
            j=j+1;
            }
        }
        return j;
    }
    public void completeOrder(ArrayList<Integer> orderAsInt){
        for (int i = 0; i < 6; i++) {
            this.resources[i] = this.resources[i] - orderAsInt.get(i);
        }
        this.score=this.score+1;
    }
    public int checkIfNeededResourceToCompleteOrderFromOtherPlayer(ArrayList<Integer> orderAsInt){
        for (int i = 0; i < 6; i++) {
            if(this.resources[i] - orderAsInt.get(i)<0){
                return i;
            }
        }
        return -1;
    }
    public int checkIfAvailableResourceInTrade(int j){
        if(this.resources[j]-1>0){
            return 1;
        }
        return 0;
    }
    public int maxResource(){
        int max=0;
        int index=-1;
        for(int i=0;i<6;i++){
            if(this.resources[i]>max) {
                max = this.resources[i];
                index = i;
            }
        }
        return index;
    }

    public int decisionMakerOnOrders(ArrayList<Integer> orderAsInt){
        int j=isOrderPossible(orderAsInt);
        if(j==0){
            completeOrder(orderAsInt);
            return -1;
        } else if(j==1){
            for(int i=0;i<6;i++){
                if(this.resources[i] < orderAsInt.get(i))
                    return i;
            }
        }else{
            this.score=this.score-1;
            return -2;
        }
        return -3;
    }
}
