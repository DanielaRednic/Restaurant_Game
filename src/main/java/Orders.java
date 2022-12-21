import utils.FileHandler;

import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Arrays;
import java.util.Map;
import java.util.Random;

public class Orders 
{
    private Map<Integer, Vector<String>> resources = new ConcurrentHashMap<>();

    Orders() 
    {
        initialiseOrders();
    }

    //This method reads the orders from the JSON file.
    private void initialiseOrders() 
    {
        FileHandler fl = FileHandler.fileHandlerInit("orders.json");
        try
        {
            this.resources = fl.readOrdersGSON();
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }

    //This method returns a random number.
    private int getRandomNumber(int upperbound)
    {
        Random random = new Random();
        return random.nextInt(upperbound);
    }

    //This method assigns a random order to a player.
    public synchronized Vector<Integer> givePlayerOrder()
    {
        Vector<Integer> orderAsInt = new Vector<>(Arrays.asList(0, 0, 0, 0, 0, 0));
        Vector<String> order = this.resources.get(getRandomNumber(29) + 1);
        //System.out.println(order);

        for (String neededResource : order)
        {
            switch (neededResource)
            {
                case "fruit":
                {
                    orderAsInt.set(0, orderAsInt.get(0) + 1);
                    break;
                }
                case "vegetables":
                {
                    orderAsInt.set(1, orderAsInt.get(1) + 1);
                    break;
                }
                case "meat":
                {
                    orderAsInt.set(2, orderAsInt.get(2) + 1);
                    break;
                }
                case "dairy":
                {
                    orderAsInt.set(3, orderAsInt.get(3) + 1);
                    break;
                }
                case "pastries":
                {
                    orderAsInt.set(4, orderAsInt.get(4) + 1);
                    break;
                }
                case "grains":
                {
                    orderAsInt.set(5, orderAsInt.get(5) + 1);
                    break;
                }
                default:
                    throw new IllegalArgumentException("Invalid resource: " + neededResource);
            }
        }
        return orderAsInt;
    }
}
