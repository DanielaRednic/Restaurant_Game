package utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import com.google.gson.Gson;

public class FileHandler
{
    //writing to "LastGameState.json" the players score,
    //remaining resources and number of trades
    private static class Order
    {
        public Vector<Vector<String>> orders;
        public String toString()
        {
            StringBuilder new_string = new StringBuilder();
            for (Vector<String> resourceList : orders)
            {
                for (String resource : resourceList)
                    new_string.append(resource).append(" ");
                new_string.append("\n");
            }
        return new_string.toString();
        }
    }

    private final String path;

    public FileHandler(String path) {
        this.path = path;
    }

    static public FileHandler fileHandlerInit(String path) {
        return new FileHandler(path);
    }

    public Map<Integer, Vector<String>> readOrdersGSON() throws FileNotFoundException {
        Map<Integer, Vector<String>> orders_map = new ConcurrentHashMap<>();
        Gson gson = new Gson();
        BufferedReader bufferedReader = new BufferedReader(
                new FileReader(this.path));
        Order orders = gson.fromJson(bufferedReader, Order.class);
        AtomicInteger counter = new AtomicInteger(0);
        for(Vector<String> order: orders.orders) {
            orders_map.put(counter.incrementAndGet(), order);
        }
        return orders_map;
    }

}


