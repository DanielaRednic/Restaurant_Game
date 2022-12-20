import java.util.*;

public class Resources
{
    public Resources()
    {}

    //This method gives the name of the resource
    public synchronized String getResourceName(int index)
    {
        if (index == 0)
        {
            return "fruits";
        }
        else if (index == 1)
        {
            return "vegetables";
        }
        else if (index == 2)
        {
            return "meat";
        }
        else if (index == 3)
        {
            return "dairy";
        }
        else if (index == 4)
        {
            return "pastries";
        }
        else if (index == 5)
        {
            return "grains";
        }
        return "Invalid index";
    }

    //This method checks that a player does not exceed 20 resources at the start of the game.
    public boolean checkResourceAmount(Vector<Integer> resources)
    {
        int resourcesAmount = 0;
        for(int i = 0; i < resources.size(); i++)
        {
            resourcesAmount = resourcesAmount + resources.get(i);
        }

        if(resourcesAmount == 20)
        {
            return true;
        }
        else if (resourcesAmount > 20)
        {
            return false;
        }
        else
        {
            return false;
        }
    }

    //This method assigns each player random resources at the start of each new game.
    public synchronized Vector<Integer> getPlayerResources()
    {
        int min = 0;
        int max = 5;
        Vector<Integer> playerResources = new Vector<>(6);
        for(int i = 0; i < 6; i++)
        {
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