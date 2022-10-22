package OOP.Lab1.Train;

import OOP.Lab1.Comparators.AscendingComparator;

import java.util.ArrayList;
import java.util.Random;

public class Train {
   public Train(int length){
        if(length<1)
            throw new IllegalArgumentException("Length of a train cannot be less than 1");
        this.length = length;
        createTrain();
    }
    public int calculateNumberOfPassengers(){
       int res=0;
       for(int i=0; i<length;i++)
           res+=wagons.get(i).maxNumberOfPassengers;
       return res;
    }
    public int calculateNumberOfBags(){
        int res=0;
        for(int i=0; i<length;i++)
            res+=wagons.get(i).maxNumberOfBags;
        return res;
    }
    public void sortWagonsByComfort(){
       wagons.sort(new AscendingComparator());
    }
    private void createTrain(){
       wagons = new ArrayList<Wagon>();
       wagons.add(new LeadingWagon());
        Random rand = new Random();
       for(int i=1; i<length;i++)
       {
           int type = rand.nextInt(3);
           switch (type){
               case 0:
                   wagons.add(new EconomyWagon());
                   break;
               case 1:
                   wagons.add(new BusinessWagon());
                   break;
               case 2:
                   wagons.add(new FirstClassWagon());
                   break;
           }
       }
    }

    public ArrayList<Wagon> getWagons() {
        return wagons;
    }
    public Wagon findWagonByPassangersRange(int i, int j)
    {

        for(int k=0; k<length;k++)
        {
            if( wagons.get(k).maxNumberOfPassengers >=i &&
                    wagons.get(k).maxNumberOfPassengers <=j)
                return wagons.get(k);

        }
        return null;
    }
    private int length;
    private ArrayList<Wagon> wagons;
}
