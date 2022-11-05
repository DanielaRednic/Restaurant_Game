package utils;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class FileHandler {
    //writing to "LastGameState.json" the players score,
    //remaining resources and number of trades
    private final String path;

    FileHandler(String path) {
        this.path = path;
    }

    public ArrayList<String> readLastGameStateJSON() {
        JSONParser parser = new JSONParser();
        ArrayList<String> lastGameStatus = new ArrayList<>();
        try {
            JSONObject JSONFile = (JSONObject) parser.parse(new FileReader(this.path));

            JSONObject game = (JSONObject) JSONFile.get("games");


            JSONObject last_state = (JSONObject) game.get("1"); //get last state


            String player1_score = (String) last_state.get("player1_score");
            lastGameStatus.add(player1_score);

            String player1_resource_count = (String) last_state.get("player1_resource_count");
            lastGameStatus.add(player1_resource_count);

            String player2_score = (String) last_state.get("player2_score");
            lastGameStatus.add(player2_score);

            String player2_resource_count = (String) last_state.get("player2_resource_count");
            lastGameStatus.add(player2_resource_count);

            String remaining_time = (String) last_state.get("remaining_time");
            lastGameStatus.add(remaining_time);


        } catch (ParseException e) {
            System.out.println("Parse error" + e.getMessage());
        } catch (IOException e) {
            System.out.println("IO File error" + e.getMessage());
        }
        return lastGameStatus;
    }

    public ArrayList<ArrayList<String>> readOrdersJSON()  {
        ArrayList<ArrayList<String>> ordersList = new ArrayList<>();
        JSONParser parser = new JSONParser();
        try {
            JSONObject jsonFile = (JSONObject) parser.parse(new FileReader(this.path)); //big object
            JSONArray orderList = (JSONArray) jsonFile.get("orders"); // orders which is an array
            for (Object resourceList : orderList) {
                ArrayList<String> stringList = new ArrayList<>();
                JSONArray ordersItem = (JSONArray) resourceList;
                for (Object resource : ordersItem) {
                    stringList.add((String) resource);
                }
                ordersList.add(stringList);
            }
        } catch (ParseException e) {
            System.out.println("Parse error" + e.getMessage());
        } catch (IOException e) {
            System.out.println("IO File error" + e.getMessage());
        }
        return ordersList;
    }

    private void printOrdersList(ArrayList<ArrayList<String>> ordersList) {
        for (ArrayList<String> resourceList : ordersList) {
            for (String resource : resourceList)
                System.out.print(resource + " ");
            System.out.println();
        }

    }

    private void printLastGameStatus(ArrayList<String> list) {
        for(String string : list)
            System.out.println(string);
    }

}


