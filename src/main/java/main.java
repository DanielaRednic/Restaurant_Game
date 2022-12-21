import utils.FileHandler;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import java.nio.charset.StandardCharsets; 


public class main {
    public static void main(String[] args) throws Exception
    {
        System.out.println("##### Starting Game #####");
        System.out.println("##### Game time: 30s ####");
        FileHandler fl = new FileHandler("orders.json");
        fl.readOrdersGSON();
        GameHandler lastGameStatus = GameHandler.readLastGameStateGSON();
        Player player1, player2;
        Bank bank;
        Integer time = 50;
        if (lastGameStatus.getTime() >= 0) {
            bank = lastGameStatus.getBank();
            player1 = new Player(0, bank);
            player2 = new Player(1, bank);
            time = lastGameStatus.getTime();
        } else {
            bank = new Bank(2);

            player1 = new Player(1, bank);
            player2 = new Player(2, bank);
        }
        Thread p1_thread = new Thread(player1);
        Thread p2_thread = new Thread(player2);
        
        p1_thread.start();
        p2_thread.start();

        try{
            final Timer timer = new Timer();

            Integer finalTime = time;
            timer.scheduleAtFixedRate(new TimerTask()
                {
                    int i = finalTime; // Time in seconds

                    public void run()
                    {
                        System.out.println("Time left: " + i-- + " seconds");
                        try {
                            GameHandler.writeLastGameState(player1, player2,i, bank);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        if (i < 0)
                        {
                            timer.cancel();
                            player1.setRun(false);
                            player2.setRun(false);
                            String QUEUE_NAME = "hello";

                            ConnectionFactory factory = new ConnectionFactory();
                            factory.setHost("localhost");
                            // factory.setPort(8001);
                            try (Connection connection = factory.newConnection();
                                 Channel channel = connection.createChannel()) 
                            {
                                channel.queueDeclare(QUEUE_NAME, false, false, false, null);
                                String message = "Exit";
                                channel.basicPublish("", QUEUE_NAME, null, message.getBytes(StandardCharsets.UTF_8));
                            }
                            catch (IOException e)
                            {
                                e.printStackTrace();
                            }
                            catch (TimeoutException e)
                            {
                                e.printStackTrace();
                            }
                        }
                    }

                }, 0, 1000);
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }
}
