public class Players {

    public Players() {
        Resources resObj = new Resources();
        int player1[] = {0, 0, 0, 0, 0, 0};
        int player2[] = {0, 0, 0, 0, 0, 0};
        System.out.println("Giving players resources...");
        resObj.getPlayerResources(player1);
        System.out.println();
        resObj.getPlayerResources(player2);
    }
}
