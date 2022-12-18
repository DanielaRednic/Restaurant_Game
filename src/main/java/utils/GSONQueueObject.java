package utils;

import com.google.gson.Gson;

import java.nio.charset.StandardCharsets;
import java.util.Vector;

public class GSONQueueObject {
    private Vector<Integer> resource;
    private String player;
    private boolean score;

    private GSONQueueObject(Vector<Integer> resource, String player, boolean score) {
        this.resource = resource;
        this.player = player;
        this.score = score;
    }

    public static byte[] createQueueObject(Vector<Integer> resource, String player, boolean score) {
        Gson gson = new Gson();
        String stringJson =  gson.toJson(new GSONQueueObject(resource, player, score));
        return stringJson.getBytes(StandardCharsets.UTF_8);
    }


}
