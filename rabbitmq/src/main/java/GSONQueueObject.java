
import com.google.gson.Gson;

import java.nio.charset.StandardCharsets;
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
    @Override
    public String toString() {
        StringBuilder player = new StringBuilder(this.player + " ingredients: ");
        int resourceIndex = 0;
        for (Integer resource : this.resource) {
            if(resource == 0)
                continue;
            switch (resourceIndex) {
                case 0: {
                    player.append(resource).append(" fruits,");
                    break;
                }
                case 1: {
                    player.append(resource).append(" vegetables,");
                    break;
                }

                case 2: {
                    player.append(resource).append(" meat,");
                    break;
                }
                case 3: {
                    player.append(resource).append(" dairy,");
                    break;
                }
                case 4: {
                    player.append(resource).append(" pastries,");
                    break;
                }
                case 5: {
                    player.append(resource).append(" grains,");
                    break;
                }
            }
            resourceIndex++;
        }
        return player.toString();
    }

}
