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

    public boolean checkResourceAmount(int [] array)
    {
        int sum = 0;
        for(int i = 0; i < array.length; i++)
        {
            sum = sum + array[i];
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

    public int [] getPlayerResources(int [] array) {
        int min = 0;
        int max = 5;

        for(int i= 0; i< array.length; i++) {
            //array[i] = (int)Math.floor(Math.random() * (max - min + 1) + min);
            array[i] = 4;
        }

        do
        {
            int rand = (int)Math.floor(Math.random() * (max - min + 1) + min);
            if (array[rand] > 2)
            {
                array[rand] = array[rand] - 1;
            }
        }while (checkResourceAmount(array) != true);

        for(int i= 0; i< array.length; i++) {
            System.out.print(array[i]);
        }

        return array;
    }


}
