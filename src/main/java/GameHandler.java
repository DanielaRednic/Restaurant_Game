import com.google.gson.Gson;
import utils.FileHandler;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Vector;

public class GameHandler {
    private String player1_score;
    private String player2_score;
    private String player1_resource_count;
    private String player2_resource_count;
    private String remaining_time;

    public GameHandler readLastGameStateGSON() throws FileNotFoundException {
        Gson gson = new Gson();
        BufferedReader bufferedReader = new BufferedReader(
                new FileReader("LastGameState.json"));
        GameHandler game = gson.fromJson(bufferedReader, GameHandler.class);
        return new GameHandler();
    }

    public GameHandler writeLastGameState(Player player1, Player player) {
    return new GameHandler();
    }
    //Writes to "LastGameState.txt" in case of crash
}
