import java.util.Vector;

public class Player implements Runnable{
    public int score;
    boolean run = true;
    Bank bank;
    int playerNo;

    public Player(int playerNo,Bank bank)
    {
        this.score = 0;
        this.bank=bank;
        this.playerNo=playerNo;
    }

    public void completeOrder(Vector<Integer> orderAsInt)
    {
        bank.pay(playerNo,orderAsInt);
        this.score=this.score+1;
    }

    public int decisionMakerOnOrders(Vector<Integer> orderAsInt)
    {
        int j=bank.isOrderPossible(playerNo,orderAsInt);

        if(j==0)
        {
            completeOrder(orderAsInt);
            bank.givePlayerResources(playerNo);
            return -1; //order is complete
        } 
        else if(j==1)
        {
            int resourceNeeded=bank.getNeededResource(playerNo,orderAsInt);
            int trade=bank.trade(playerNo,resourceNeeded);
            if(trade==1)
            {
                if(bank.isOrderPossible(playerNo,orderAsInt)==0)
                {
                    completeOrder(orderAsInt);
                    return -1;
                }
                else
                {
                    return -2;
                }
            }
            else
            {
                return -2;
            }
        }
        else
        {
            return -2; //order cant be done
        }
    }

    public void setRun(boolean toSet)
    {
        run = false;
    }
    @Override
    public void run()
    {
        int resolved_case;
        Vector<Integer> order ;
        Orders order1 = new Orders();
        while(run)
        {
            order = order1.givePlayerOrder();
            resolved_case = decisionMakerOnOrders(order);

            System.out.println(order+" "+resolved_case + " " + this.bank.getResources(playerNo));
            if(resolved_case==-1) {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }else {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
