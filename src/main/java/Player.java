import java.util.Vector;

public class Player {
    public Vector<Integer> resources= new Vector<>(6);
    public int score;

    public Player() {
        Resources resObj = new Resources();
        this.resources =new Vector<>(resObj.getPlayerResources(resources));
        this.score=0;
    }

    private Vector<Integer> isOrderPossibleHelper(Vector<Integer> orderAsInt){
        Vector<Integer>k =new Vector<>(6);
        for (int i = 0 ; i < 6 ; i++){
            k.add(i,this.resources.get(i)-orderAsInt.get(i));
        }
        return k;
    }
    private int isOrderPossible(Vector<Integer> orderAsInt) {
        int j=0;
        Vector<Integer> k=new Vector<>(isOrderPossibleHelper(orderAsInt));
        for(int i=0;i<6;i++){
            if(k.get(i)<0){
                j=j+1;
            }
        }
        return j;
    }
    public void completeOrder(Vector<Integer> orderAsInt){
        for (int i = 0; i < 6; i++) {
            this.resources.add(i,this.resources.get(i)-orderAsInt.get(i));
        }
        this.score=this.score+1;
    }
    public int checkIfNeededResourceToCompleteOrderFromOtherPlayer(Vector<Integer> orderAsInt){
        for (int i = 0; i < 6; i++) {
            if(this.resources.get(i) - orderAsInt.get(i)<0){
                return i;
            }
        }
        return -1;
    }
    public int checkIfAvailableResourceInTrade(int j){
        if(this.resources.get(j)-1>0){
            return 1;
        }
        return 0;
    }
    public int maxResource(){
        int max=0;
        int index=-1;
        for(int i=0;i<6;i++){
            if(this.resources.get(i)>max) {
                max = this.resources.get(i);
                index = i;
            }
        }
        return index;
    }

    public int decisionMakerOnOrders(Vector<Integer> orderAsInt){
        int j=isOrderPossible(orderAsInt);
        if(j==0){
            completeOrder(orderAsInt);
            return -1;
        } else if(j==1){
            for(int i=0;i<6;i++){
                if(this.resources.get(i) < orderAsInt.get(i))
                    return i;
            }
        }else{
            this.score=this.score-1;
            return -2;
        }
        return -3;
    }
}
