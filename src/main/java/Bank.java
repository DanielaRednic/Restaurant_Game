import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

public class Bank 
{
    Map<Integer, Vector<Integer>> resourcesforeachplayer= new ConcurrentHashMap<>();
    Vector<Integer> resources=new Vector<>(6,0);
    Resources resObj = new Resources();
    int nrofplayers;

    public Bank(int nrofplayers)
    {
        this.nrofplayers=nrofplayers;
        for(int i = 1; i<=nrofplayers;i++)
        {
            this.resourcesforeachplayer.put(i,resObj.getPlayerResources());
        }
    }

    //Helper method for isOrderPossible
    //We create a vector with the needed resources for the order
    private Vector<Integer> isOrderPossibleHelper(int playerNo,Vector<Integer> orderAsInt)
    {
        Vector<Integer>k =new Vector<>(6);
        for (int i = 0 ; i < 6 ; i++)
        {
            Vector<Integer> p=this.resourcesforeachplayer.get(playerNo);
            k.add(i,p.get(i)-orderAsInt.get(i));
        }
        return k;
    }

    //Method for checking if a player can complete an order.
    public int isOrderPossible(int playerNo,Vector<Integer> orderAsInt)
    {
        int j=0;
        Vector<Integer> k=new Vector<>(isOrderPossibleHelper(playerNo,orderAsInt));
        for(int i=0;i<6;i++)
        {
            if(k.get(i)<0)
            {
                j=j+1;
            }
        }
        return j;
    }

    //Method that returns the resource a players wants to obtain from trading.
    public int getNeededResource(int playerNo,Vector<Integer> orderAsInt)
    {
        for(int i=0;i<6;i++)
        {
            if(this.resourcesforeachplayer.get(playerNo).get(i) < orderAsInt.get(i))
                return i; //returns needed resource for trade
        }
        return -1;
    }

    //Method for paying resources to the bank.
    public void pay(int playerNo, Vector<Integer> orderAsInt)
    {
        for (int i = 0; i < 6; i++) 
        {
            this.resourcesforeachplayer.get(playerNo).set(i,this.resourcesforeachplayer.get(playerNo).get(i)-orderAsInt.get(i));
        }
    }

    //Method that rewards the players 3 random resources after they complete an order.
    public synchronized void givePlayerResources(int playerNo){
        int min = 0;
        int max = 5;
        int rand = (int)Math.floor(Math.random() * (max - min + 1) + min);
        int rand2= (int)Math.floor(Math.random() * (max - min + 1) + min);
        int rand3= (int)Math.floor(Math.random() * (max - min + 1) + min);
        this.resourcesforeachplayer.get(playerNo).set(rand,this.resourcesforeachplayer.get(playerNo).get(rand)+1);
        this.resourcesforeachplayer.get(playerNo).set(rand2,this.resourcesforeachplayer.get(playerNo).get(rand2)+1);
        this.resourcesforeachplayer.get(playerNo).set(rand3,this.resourcesforeachplayer.get(playerNo).get(rand3)+1);
    }

    //Method that handles the trading between players.
    public synchronized int trade(int playerNo,int resourceNeeded)
    {
        System.out.println("##### Trading #####");
        int max=0;
        int resourcemax=0;
        for(int i=1;i<=nrofplayers;i++)
        {
            if(this.resourcesforeachplayer.get(i).get(resourceNeeded)>resourcemax)
            {
                resourcemax=this.resourcesforeachplayer.get(i).get(resourceNeeded);
                max=i;
            }
        }
        int maxforcurrentplayer=0;
        int indexmaxforcurrentplayer=-1;
        for(int i=0;i<6;i++)
        {
            if(this.resourcesforeachplayer.get(playerNo).get(i)>maxforcurrentplayer)
            {
                maxforcurrentplayer=this.resourcesforeachplayer.get(playerNo).get(i);
                indexmaxforcurrentplayer=i;
            }
        }
        if(max>0&&indexmaxforcurrentplayer>0&&resourcemax>0&&maxforcurrentplayer>0)
        {
            this.resourcesforeachplayer.get(playerNo).set(resourceNeeded,this.resourcesforeachplayer.get(playerNo).get(resourceNeeded)+1);
            this.resourcesforeachplayer.get(max).set(resourceNeeded,this.resourcesforeachplayer.get(max).get(resourceNeeded)-1);
            this.resourcesforeachplayer.get(max).set(indexmaxforcurrentplayer,this.resourcesforeachplayer.get(max).get(indexmaxforcurrentplayer)+1);
            this.resourcesforeachplayer.get(playerNo).set(indexmaxforcurrentplayer,this.resourcesforeachplayer.get(playerNo).get(indexmaxforcurrentplayer)-1);
            return 1;
        }
        return -1;
    }

    public Vector<Integer> getResources(int playerNo)
    {
        return this.resourcesforeachplayer.get(playerNo);
    }
}
