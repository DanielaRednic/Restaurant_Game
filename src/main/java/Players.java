public class Players {
    public int player1[] = {0, 0, 0, 0, 0, 0};
    public int player2[] = {0, 0, 0, 0, 0, 0};
    public Players() {
        Resources resObj = new Resources();
        System.out.println("Giving players resources...");
        resObj.getPlayerResources(player1);
        System.out.println();
        resObj.getPlayerResources(player2);
        System.out.println();
    }
}
