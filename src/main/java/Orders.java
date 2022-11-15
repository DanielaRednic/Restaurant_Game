import utils.FileHandler;

import java.util.ArrayList;
import java.util.Vector;
import java.util.Arrays;
import java.util.Random;

public class Orders {
    //gets the orders from the FileHandler
    private Vector<Vector<String>> resources;

    Orders() {
        initialiseOrders();
    }

    private void initialiseOrders() {
        FileHandler fl = FileHandler.fileHandlerInit("orders.json");
        this.resources = fl.readOrdersJSON();
    }

    private int getRandomNumber(int upperbound) {
        Random random = new Random();
        return random.nextInt(upperbound);
    }

    public Vector<Integer> givePlayerOrder() {
        Vector<Integer> orderAsInt = new Vector<>(Arrays.
                                                            asList(0, 0, 0, 0, 0, 0));
        Vector<String> order = this.resources.get(getRandomNumber(29)); //repair orders.json, check which order is missing
        System.out.println(order);
        for (String neededResource : order) {
            switch (neededResource) {
                case "fruit": {
                    orderAsInt.set(0, orderAsInt.get(0) + 1);
                    break;
                }
                case "vegetables": {
                    orderAsInt.set(1, orderAsInt.get(1) + 1);
                    break;
                }
                case "meat": {
                    orderAsInt.set(2, orderAsInt.get(2) + 1);
                    break;
                }
                case "dairy": {
                    orderAsInt.set(3, orderAsInt.get(3) + 1);
                    break;
                }
                case "pastries": {
                    orderAsInt.set(4, orderAsInt.get(4) + 1);
                    break;
                }
                case "grains": {
                    orderAsInt.set(5, orderAsInt.get(5) + 1);
                    break;
                }
                default:
                    throw new IllegalArgumentException("Invalid resource: " + neededResource);
            }
        }
        return orderAsInt;
    }
    public static void main(String[] args) {
        Orders order = new Orders();
        Vector<Integer> bla = order.givePlayerOrder();
        System.out.println(bla);
    }

}
