import java.io.IOException;
import java.util.Arrays;
import java.util.Vector;

import com.google.gson.Gson;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import utils.GSONQueueObject;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

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
        String QUEUE_NAME = "hello";

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);

            byte[] message = GSONQueueObject.createQueueObject(this.bank.resources, "Player" + this.playerNo, true);
            channel.basicPublish("", QUEUE_NAME, null, message);

           // System.out.println(" [x] Sent '" + Arrays.toString(message) + "'");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }



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

    public int getNumberOfResource() {
        Vector<Integer> resources =  this.bank.getResources(playerNo);
        Integer nrOfResource = 0;
        for(Integer resource : resources)
        {
            nrOfResource += resource;
        }
        return nrOfResource;
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
