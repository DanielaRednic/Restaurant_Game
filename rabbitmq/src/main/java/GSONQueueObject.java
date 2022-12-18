import com.google.gson.Gson;
import java.util.Vector;

public class GSONQueueObject {
    private Vector<Integer> resource;
    private String player;
    private boolean score;

    public String getPlayer() {
        return this.player;
    }
    public Vector<Integer> getResource() {
        return this.resource;
    }
    public boolean getScore() {
        return this.score;
    }

    private GSONQueueObject(Vector<Integer> resource, String player, boolean score) {
        this.resource = resource;
        this.player = player;
        this.score = score;
    }

    public static GSONQueueObject  readQueueObject(String jsonString) {
        Gson gson = new Gson();
        return gson.fromJson(jsonString, GSONQueueObject.class);
    }


}
