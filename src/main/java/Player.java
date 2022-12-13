import java.io.IOException;
import java.util.Vector;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class Player implements Runnable{
    public int score;
    boolean run = true;
    Bank bank;
    int playerNo;
    public Player(int playerNo,Bank bank) {
        this.score = 0;
        this.bank=bank;
        this.playerNo=playerNo;
    }



    public void completeOrder(Vector<Integer> orderAsInt){
        bank.pay(playerNo,orderAsInt);
        this.score=this.score+1;
        String QUEUE_NAME = "hello";

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            String message = "Player" + playerNo + "|1";
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes(StandardCharsets.UTF_8));

            System.out.println(" [x] Sent '" + message + "'");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }



    }
//    public int checkIfNeededResourceToCompleteOrderFromOtherPlayer(Vector<Integer> orderAsInt){
//        for (int i = 0; i < 6; i++) {
//            if(this.resources.get(i) - orderAsInt.get(i)<0){
//                return i;
//            }
//        }
//        return -1;
//    }
//    public int checkIfAvailableResourceInTrade(int j){
//        if(this.resources.get(j)-1>0){
//            return 1;
//        }
//        return 0;
//    }
//    public int maxResource(){
//        int max=0;
//        int index=-1;
//        for(int i=0;i<6;i++){
//            if(this.resources.get(i)>max) {
//                max = this.resources.get(i);
//                index = i;
//            }
//        }
//        return index;
//    }

    public int decisionMakerOnOrders(Vector<Integer> orderAsInt){
        int j=bank.isOrderPossible(playerNo,orderAsInt);
        if(j==0){
            completeOrder(orderAsInt);
            return -1; //order is complete
        } else if(j==1){
            int resourceNeeded=bank.getNeededResource(playerNo,orderAsInt);
            int trade=bank.trade(playerNo,resourceNeeded);
            if(trade==1){
                if(bank.isOrderPossible(playerNo,orderAsInt)==0) {
                    completeOrder(orderAsInt);
                    return -1;
                }else{
                    this.score=this.score-1;
                    return -2;
                }
            }else{
                this.score=this.score-1;
                return -2;
            }
        }else{
            this.score=this.score-1;
            return -2; //order cant be done
        }
    }

    public void setRun(boolean toSet) {
        run = false;
    }
    public synchronized Integer updateEnemyResource() {

                          return 0;
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
    public void run(){
        int resolved_case;
        Vector<Integer> order ;
        Orders order1 = new Orders();
        while(run){
            order = order1.givePlayerOrder();
            resolved_case = decisionMakerOnOrders(order);

            System.out.println(order+" "+resolved_case + " " + this.bank.getResources(playerNo));

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
