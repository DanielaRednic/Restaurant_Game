import com.google.gson.Gson;
import java.io.*;

public class GameHandler {
    private final int player1_score;
    private final int player2_score;
    private final int  player1_resource_count;
    private final int player2_resource_count;
    private final int remaining_time;

    private GameHandler(Player player1, Player player2, Integer time) {
        player1_score = player1.score;
        player2_score = player2.score;

        player1_resource_count = player1.getNumberOfResource();
        player2_resource_count = player2.getNumberOfResource();

        remaining_time = time;
    }

    public static GameHandler readLastGameStateGSON() throws FileNotFoundException {
        Gson gson = new Gson();
        BufferedReader bufferedReader = new BufferedReader(
                new FileReader("LastGameState.json"));
        return gson.fromJson(bufferedReader, GameHandler.class);
    }

    public static int writeLastGameState(Player player1, Player player2, Integer time) throws IOException {
        Gson gson = new Gson();
        gson.toJson(new GameHandler(player1, player2, time), new FileWriter("LastGameState.json"));
        return 0;
    }
    //Writes to "LastGameState.txt" in case of crash
}
