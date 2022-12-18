package utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import com.google.gson.Gson;

public class FileHandler {
    //writing to "LastGameState.json" the players score,
    //remaining resources and number of trades
    private static class Order{
        public Vector<Vector<String>> orders;
        public String toString() {
            StringBuilder new_string = new StringBuilder();
            for (Vector<String> resourceList : orders) {
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

//    public Vector<String> readLastGameStateJSON() {
//        JSONParser parser = new JSONParser();
//        Vector<String> lastGameStatus = new Vector<>();
//        try {
//            JSONObject JSONFile = (JSONObject) parser.parse(new FileReader(this.path));
//
//            JSONObject game = (JSONObject) JSONFile.get("games");
//
//
//            JSONObject last_state = (JSONObject) game.get("1"); //get last state
//
//
//            String player1_score = (String) last_state.get("player1_score");
//            lastGameStatus.add(player1_score);
//
//            String player1_resource_count = (String) last_state.get("player1_resource_count");
//            lastGameStatus.add(player1_resource_count);
//
//            String player2_score = (String) last_state.get("player2_score");
//            lastGameStatus.add(player2_score);
//
//            String player2_resource_count = (String) last_state.get("player2_resource_count");
//            lastGameStatus.add(player2_resource_count);
//
//            String remaining_time = (String) last_state.get("remaining_time");
//            lastGameStatus.add(remaining_time);
//
//
//        } catch (ParseException e) {
//            System.out.println("Parse error" + e.getMessage());
//        } catch (IOException e) {
//            System.out.println("IO File error" + e.getMessage());
//        }
//        return lastGameStatus;
//    }
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
//    public Vector<Vector<String>> readOrdersJSON()  {
//        Vector<Vector<String>> ordersList = new Vector<>();
//        JSONParser parser = new JSONParser();
//        try {
//            JSONObject jsonFile = (JSONObject) parser.parse(new FileReader(this.path)); //big object
//            JSONArray orderList = (JSONArray) jsonFile.get("orders"); // orders which is an array
//            for (Object resourceList : orderList) {
//                Vector<String> stringList = new Vector<>();
//                JSONArray ordersItem = (JSONArray) resourceList;
//                for (Object resource : ordersItem) {
//                    stringList.add((String) resource);
//                }
//                ordersList.add(stringList);
//            }
//        } catch (ParseException e) {
//            System.out.println("Parse error" + e.getMessage());
//        } catch (IOException e) {
//            System.out.println("IO File error" + e.getMessage());
//        }
//        return ordersList;
//    }

    private void printOrdersList(Vector<Vector<String>> ordersList) {
        for (Vector<String> resourceList : ordersList) {
            for (String resource : resourceList)
                System.out.print(resource + " ");
            System.out.println();
        }

    }

    private void printLastGameStatus(Vector<String> list) {
        for(String string : list)
            System.out.println(string);
    }

    public static void Main(String[] args) throws FileNotFoundException {
        FileHandler util = new FileHandler("orders.json");
        util.readOrdersGSON();
    }
}


