package OOP.Lab1.Tests;
import OOP.Lab1.Train.Train;
import OOP.Lab1.Train.Wagon;
import org.junit.BeforeClass;

import java.util.ArrayList;

import static org.junit.Assert.*;
public class Test {
        @BeforeClass
    public static void setUp(){
            train = new Train(SIZE);

        }

        @org.junit.Test
        public void testNumberOfPassengers()
        {
            int res=0;
            ArrayList<Wagon> wagons = train.getWagons();
            int length = wagons.size();
            for(int i=0; i<length;i++)
                res+=wagons.get(i).getMaxNumberOfPassengers();
            assertEquals(res,train.calculateNumberOfPassengers());
        }
    @org.junit.Test
    public void testNumberOfBags()
    {
        int res=0;
        ArrayList<Wagon> wagons = train.getWagons();
        int length = wagons.size();
        for(int i=0; i<length;i++)
            res+=wagons.get(i).getMaxNumberOfBags();
        assertEquals(res,train.calculateNumberOfBags());
    }
    @org.junit.Test
    public void testFindingWagon()
    {
        int res=0;
        int a = 10;
        int b = 30;
        ArrayList<Wagon> wagons = train.getWagons();
        Wagon wg =null;
        int length = wagons.size();
        for(int i=0; i<length;i++) {
            wg = wagons.get(i);
            if (wg.getMaxNumberOfPassengers() >= a &&
                    wg.getMaxNumberOfPassengers() <= b)
                break;
        }
        assertEquals(wg,train.findWagonByPassangersRange(a,b));
    }

    @org.junit.Test
    public void testDefinitionOfTrain(){

            IllegalArgumentException e = assertThrows(IllegalArgumentException.class,()-> {
                train = new Train(-1);});
            assertEquals("Length of a train cannot be less than 1",e.getMessage());
    }

        private static Train train;
        public static final int SIZE =100;
}
