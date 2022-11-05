public class main {
    public static void main(String[] args){
        System.out.println("##### Starting Game #####");
        System.out.println("##### Game time: 90s ####");
        Thread t = new Thread();
        t.start();
        try{
            t.join(90000);
        }catch(InterruptedException e){
            System.out.println(e);
        }
        System.out.println("##### Player 1 Wins ####");
    }
}