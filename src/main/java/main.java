import utils.FileHandler;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import java.nio.charset.StandardCharsets;

public class main {
    public static void main(String[] args) throws Exception {
        System.out.println("##### Starting Game #####");
        System.out.println("##### Game time: 90s ####");
        FileHandler fl = new FileHandler("orders.json");
        fl.readOrdersGSON();
        Bank bank=new Bank(2);
        Player player1 = new Player(1,bank);
        Player player2 = new Player(2,bank);
        Thread p1_thread = new Thread(player1);
        Thread p2_thread = new Thread(player2);
        
        p1_thread.start();
        p2_thread.start();
         String QUEUE_NAME = "hello";

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
       // factory.setPort(8001);
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            String message = "Player1|1";
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes(StandardCharsets.UTF_8));

            System.out.println(" [x] Sent '" + message + "'");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        try{
            final Timer timer = new Timer();
                timer.scheduleAtFixedRate(new TimerTask() {
                    int i = 20; // Time in seconds

                    public void run() {
                        System.out.println(i--);
                        if (i < 0) {
                            timer.cancel();
                            player1.setRun(false);
                            player2.setRun(false);
                            if(player1.score >= player2.score){
                                System.out.println("##### Player 1 Wins! Score:"+ player1.score +"####");
                            }
                            else{
                                System.out.println("##### Player 2 Wins! Score:"+ player2.score +"####");
                            }
                        }
                    }
                }, 0, 5000);
        }catch(Exception e){
            System.out.println(e);
        }

        
    }
}
