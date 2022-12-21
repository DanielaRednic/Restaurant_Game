import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.Vector;
import java.io.*;

public class GameHandler {
    private final int player1_score;
    private final int player2_score;

    private final int remaining_time;
    private Bank bank;


    private GameHandler(Player player1, Player player2, Integer time, Bank bank)
    {
        player1_score = player1.score;
        player2_score = player2.score;

        remaining_time = time;

        this.bank = bank;
    }

    //This method reads the last game state from the JSON file
    public static GameHandler readLastGameStateGSON() throws FileNotFoundException
    {
        Gson gson = new Gson();
        BufferedReader bufferedReader = new BufferedReader(
                new FileReader("LastGameState.json"));
        return gson.fromJson(bufferedReader, GameHandler.class);
    }

    //This method writes the last game state to the JSON file
    public static void writeLastGameState(Player player1, Player player2, Integer time, Bank bank) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try(Writer writer = new FileWriter("LastGameState.json")) {
            gson.toJson(new GameHandler(player1, player2, time, bank), writer);
        }
    }
    //Writes to "LastGameState.txt" in case of crash
    public Integer getTime() {
        return this.remaining_time;
    }
    public Bank getBank() {
        return this.bank;
    }
}