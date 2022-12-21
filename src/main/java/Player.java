import java.io.IOException;
import java.util.Vector;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import utils.GSONQueueObject;

import java.util.concurrent.TimeoutException;

public class Player implements Runnable{
    public int score;
    boolean run = true;
    Bank bank;
    Integer playerNo;
    Resources resObj = new Resources();

    public Player(int playerNo, Bank bank)
    {
        this.score = 0;
        this.bank = bank;
        this.playerNo = playerNo;
    }

    public Vector<Integer> getPlayerResourcesFromBank()
    {
        return this.bank.getResources(playerNo);
    }

    //This method completes an order for a player and increments their score
    public synchronized void completeOrder(Vector<Integer> orderAsInt)
    {
        bank.pay(playerNo,orderAsInt);
        this.score = this.score + 1;
        String QUEUE_NAME = "hello";

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel())
             {
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            byte[] message = GSONQueueObject.createQueueObject(this.bank.getResources(this.playerNo), "Player" + this.playerNo, true);
            channel.basicPublish("", QUEUE_NAME, null, message);

            System.out.println("Player #"+ playerNo + " has completed an order!");
        }
        catch (IOException | TimeoutException e)
        {
            e.printStackTrace();
        }
    }

    /* This method decides whether an order can be completed or not.
    If it can be completed, then it calls completeOrder and rewards the player with 3 new resources.
    If it can't be completed, first it tries to trade for the needed resource and if that also fails,
    then it prints a message.*/
    public synchronized int decisionMakerOnOrders(Vector<Integer> orderAsInt)
    {
        int j = bank.isOrderPossible(playerNo,orderAsInt);

        if(j == 0)
        {
            completeOrder(orderAsInt);
            bank.givePlayerResources(playerNo);
            return -1; //order is complete
        } 
        else if(j == 1)
        {
            int resourceNeeded = bank.getNeededResource(playerNo, orderAsInt);
            int trade = bank.trade(playerNo, resourceNeeded);
            if(trade == 1)
            {
                if(bank.isOrderPossible(playerNo, orderAsInt) == 0)
                {
                    completeOrder(orderAsInt);
                    return -1;
                }
                else
                {
                    System.out.println("Player #"+playerNo+" didn't complete the order!");
                    return -2;
                }
            }
            else
            {
                System.out.println("Player #"+playerNo+" didn't complete the order!");
                return -2;
            }
        }
        else
        {
            System.out.println("Player #"+playerNo+" didn't complete the order!");
            return -2; //order cant be done
        }
    }

    //This method stops the run method of the thread.
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
        Vector<Integer> bankResourcesPerPlayer = this.bank.getResources(playerNo);

        System.out.println("Player #"+playerNo+" has the starting resources: "+resObj.getResourceName(0) + "*" +bankResourcesPerPlayer.get(0) + " "
                                                                              +resObj.getResourceName(1) + "*" +bankResourcesPerPlayer.get(1) + " "
                                                                              +resObj.getResourceName(2) + "*" +bankResourcesPerPlayer.get(2) + " "
                                                                              +resObj.getResourceName(3) + "*" +bankResourcesPerPlayer.get(3) + " "
                                                                              +resObj.getResourceName(4) + "*" +bankResourcesPerPlayer.get(4) + " "
                                                                              +resObj.getResourceName(5) + "*" +bankResourcesPerPlayer.get(5) + " ");

        while(run)
        {
            Vector<Integer> resourcesPerPlayer = this.bank.getResources(playerNo);

            order = order1.givePlayerOrder();
            String playerOrder = "";
            for(int i = 0; i < order.size(); i++)
            {
                if(order.get(i) > 0)
                {
                    playerOrder += resObj.getResourceName(i) + "*" +order.get(i) + " ";
                }
            }
            System.out.println("Player #"+playerNo+" has the order: "+ playerOrder);

            resolved_case = decisionMakerOnOrders(order);
            System.out.println("Player #"+playerNo+" - resources left: " +resObj.getResourceName(0) + "*" +resourcesPerPlayer.get(0) + " "
                                                                         +resObj.getResourceName(1) + "*" +resourcesPerPlayer.get(1) + " "
                                                                         +resObj.getResourceName(2) + "*" +resourcesPerPlayer.get(2) + " "
                                                                         +resObj.getResourceName(3) + "*" +resourcesPerPlayer.get(3) + " "
                                                                         +resObj.getResourceName(4) + "*" +resourcesPerPlayer.get(4) + " "
                                                                         +resObj.getResourceName(5) + "*" +resourcesPerPlayer.get(5) + " ");

            //Resolved case = -1 means that a player is able to complete an order.
            if(resolved_case==-1) 
            {
                try
                {
                    //The player threads take 5s to complete an order.
                    Thread.sleep(5000);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
            else
            {
                try
                {
                    //The player threads get a time-out of 10s when they cannot complete an order.
                    Thread.sleep(10000);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }
}
