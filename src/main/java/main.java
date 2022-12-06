import utils.FileHandler;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class main {
    public static void main(String[] args) throws FileNotFoundException {
        System.out.println("##### Starting Game #####");
        System.out.println("##### Game time: 90s ####");
        FileHandler fl = new FileHandler("orders.json");
        fl.readOrdersGSON();
        Player player1 = new Player();
        Player player2 = new Player();
        Thread p1_thread = new Thread(player1);
        Thread p2_thread = new Thread(player2);
        
        p1_thread.start();
        p2_thread.start();

        try{
            final Timer timer = new Timer();
                timer.scheduleAtFixedRate(new TimerTask() {
                    int i = 90; // Time in seconds

                    public void run() {
                        System.out.println(i--);
                        if (i < 0) {
                            timer.cancel();
                            player1.setRun(false);
                            player2.setRun(false);
                        }
                    }
                }, 0, 1000);
        }catch(Exception e){
            System.out.println(e);
        }
        if(player1.score >= player2.score){
            System.out.println("##### Player 1 Wins! Score:"+ player1.score +"####");
        }
        else{
            System.out.println("##### Player 2 Wins! Score:"+ player2.score +"####");
        }
        
    }
}
