import java.util.ArrayList;

public class main {
    public static void main(String[] args){
        System.out.println("##### Starting Game #####");
        System.out.println("##### Game time: 90s ####");
        Thread t = new Thread();
        t.start();
        // Players players = new Players();
        // Orders order = new Orders();
        // ArrayList<Integer> bla = order.givePlayerOrder();
        // System.out.println(bla);
        // OrderHandler orderStatus = new OrderHandler();
        // if(orderStatus.isOrderDone(players.player1, bla)){
        //     System.out.println("Order for player 1 is done");
        //     for(int i= 0; i< players.player1.length; i++) {
        //         System.out.print(players.player1[i]);
        //     }
        //     System.out.println();
        // }
        // if(orderStatus.isOrderDone(players.player2, bla)){
        //     System.out.println("Order for player 2 is done");
        //     for(int i= 0; i< players.player2.length; i++) {
        //         System.out.print(players.player2[i]);
        //     }
        //     System.out.println();
        // }
        try{
            t.join(90000);
        }catch(InterruptedException e){
            System.out.println(e);
        }
        System.out.println("##### Player 1 Wins ####");
    }
}
