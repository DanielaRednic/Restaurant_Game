import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

public class Bank 
{
    Map<Integer, Vector<Integer>> resourcesForEachPlayer= new ConcurrentHashMap<>();
    private transient ReentrantLock lock =new ReentrantLock();
    Resources resObj = new Resources();
    int numberOfPlayers; // number of players in the game

    public Bank(int numberOfPlayers)
    {
        this.numberOfPlayers = numberOfPlayers;
        for(int i = 1; i <= numberOfPlayers;i++)
        {
            this.resourcesForEachPlayer.put(i,resObj.getPlayerResources());
        }
    }
    public Bank(int numberOfPlayers,Map<Integer, Vector<Integer>>resourcesForEachPlayer){
        this.numberOfPlayers = numberOfPlayers;
        this.resourcesForEachPlayer=resourcesForEachPlayer;
    }

    //Helper method for isOrderPossible
    //We create a vector with the needed resources for the order
    private Vector<Integer> isOrderPossibleHelper(int playerNo,Vector<Integer> orderAsInt)
    {
        Vector<Integer> tmp =new Vector<>(6);
        lock.lock();
        try {

            for (int i = 0; i < 6; i++) {
                Vector<Integer> p = this.resourcesForEachPlayer.get(playerNo);
                tmp.add(i, p.get(i) - orderAsInt.get(i));
            }
        }finally{
            lock.unlock();

        }
        return tmp;
    }

    //Method for checking if a player can complete an order.
    public synchronized int isOrderPossible(int playerNo, Vector<Integer> orderAsInt)
    {
        lock.lock();
        int j = 0;
        try {
            Vector<Integer> tmp = new Vector<>(isOrderPossibleHelper(playerNo, orderAsInt));
            for (int i = 0; i < 6; i++) {
                if (tmp.get(i) < 0) {
                    j = j + 1;
                }
            }
        }finally {
            lock.unlock();
        }
        return j;
    }

    //Method that returns the resource a players wants to obtain from trading.
    public synchronized int getNeededResource(int playerNo, Vector<Integer> orderAsInt)
    {
        lock.lock();
        int j=-1;
        try {
            for (int i = 0; i < 6; i++) {
                if (this.resourcesForEachPlayer.get(playerNo).get(i) < orderAsInt.get(i)) {
                    j = i; //returns needed resource for trade
                }
            }
        }finally {
            lock.unlock();
        }
        return j;
    }

    //Method for paying resources to the bank.
    public synchronized void pay(int playerNo, Vector<Integer> orderAsInt)
    {
        lock.lock();
        try {
            for (int i = 0; i < 6; i++) {
                this.resourcesForEachPlayer.get(playerNo).set(i, this.resourcesForEachPlayer.get(playerNo).get(i) - orderAsInt.get(i));
            }
        }finally {
            lock.unlock();
        }
    }

    //Method that rewards the players 3 random resources after they complete an order.
    public synchronized void givePlayerResources(int playerNo){
        lock.lock();
        try {
            int min = 0;
            int max = 5;
            int rand = (int) Math.floor(Math.random() * (max - min + 1) + min);
            int rand2 = (int) Math.floor(Math.random() * (max - min + 1) + min);
            int rand3 = (int) Math.floor(Math.random() * (max - min + 1) + min);
            this.resourcesForEachPlayer.get(playerNo).set(rand, this.resourcesForEachPlayer.get(playerNo).get(rand) + 1);
            this.resourcesForEachPlayer.get(playerNo).set(rand2, this.resourcesForEachPlayer.get(playerNo).get(rand2) + 1);
            this.resourcesForEachPlayer.get(playerNo).set(rand3, this.resourcesForEachPlayer.get(playerNo).get(rand3) + 1);
        }finally {
            lock.unlock();
        }
    }

    //Method that handles the trading between players.
    public synchronized int trade(int playerNo, int resourceNeeded)
    {

        lock.lock();
        int return_code=-1;
        try {
            System.out.println("##### Trading #####");
            int traderTwo = 0; //This variable represents the player who has the biggest amount of resourceNeeded type
            int mostOfResourceNeeded = 0;
            for (int i = 1; i <= numberOfPlayers; i++) {
                if (this.resourcesForEachPlayer.get(i).get(resourceNeeded) > mostOfResourceNeeded) {
                    mostOfResourceNeeded = this.resourcesForEachPlayer.get(i).get(resourceNeeded);
                    traderTwo = i;
                }
            }
            int disposableResource = 0;
            int indexMaxForCurrentPlayer = -1;
            for (int i = 0; i < 6; i++) {
                if (this.resourcesForEachPlayer.get(playerNo).get(i) > disposableResource) {
                    disposableResource = this.resourcesForEachPlayer.get(playerNo).get(i);
                    indexMaxForCurrentPlayer = i;
                }
            }
            if (traderTwo > 0 && indexMaxForCurrentPlayer > 0 && mostOfResourceNeeded > 0 && disposableResource > 0) {
                this.resourcesForEachPlayer.get(playerNo).set(resourceNeeded, this.resourcesForEachPlayer.get(playerNo).get(resourceNeeded) + 1);
                this.resourcesForEachPlayer.get(traderTwo).set(resourceNeeded, this.resourcesForEachPlayer.get(traderTwo).get(resourceNeeded) - 1);
                this.resourcesForEachPlayer.get(traderTwo).set(indexMaxForCurrentPlayer, this.resourcesForEachPlayer.get(traderTwo).get(indexMaxForCurrentPlayer) + 1);
                this.resourcesForEachPlayer.get(playerNo).set(indexMaxForCurrentPlayer, this.resourcesForEachPlayer.get(playerNo).get(indexMaxForCurrentPlayer) - 1);
                System.out.println("Player #" + traderTwo + " gave Player#" + playerNo + " [" + resObj.getResourceName(resourceNeeded) + "] and got [" + resObj.getResourceName(indexMaxForCurrentPlayer) + "] in return.");
                return_code = 1;
            }

            if (return_code == -1) {
                System.out.println("##### Trading failed #####");
            }
        }finally{
            lock.unlock();
        }
        return return_code;
    }

    public Vector<Integer> getResources(int playerNo)
    {
        return this.resourcesForEachPlayer.get(playerNo);
    }
}
