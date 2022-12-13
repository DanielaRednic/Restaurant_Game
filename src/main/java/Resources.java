import java.util.*;

public class Resources {
    
    public Resources() {
    }
    
    //This enum is used for remembering the order of the resources inside of the array
    public enum ResourceType {
        fruit,
        vegetables,
        meat,
        dairy,
        pastries,
        grains
    };

    public boolean checkResourceAmount(Vector<Integer> resources)
    {
        int sum = 0;
        for(int i = 0; i < resources.size(); i++)
        {
            sum = sum + resources.get(i);
        }

        if(sum == 20)
        {
            return true;
        }
        else if (sum > 20)
        {
            return false;
        }
        else
        {
            return false;
        }
    }

    public Vector<Integer> getPlayerResources() {
        int min = 0;
        int max = 5;
        Vector<Integer> playerResources=new Vector<>(6);
        for(int i = 0; i < 6/*playerResources.capacity()*/; i++) {
            playerResources.add(i, 4);
        }

        do
        {
            int rand = (int)Math.floor(Math.random() * (max - min + 1) + min);
            if (playerResources.get(rand) > 2)
            {
                playerResources.set(rand,  playerResources.get(rand)-1);
            }
        }while (checkResourceAmount(playerResources) != true);

        return playerResources;
    }


}
