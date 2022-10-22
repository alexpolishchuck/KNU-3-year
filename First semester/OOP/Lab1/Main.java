package OOP.Lab1;
import OOP.Lab1.Train.*;
public class Main {
    public static void main(String[]args){
        Train train = new Train(5);
        System.out.println(train.calculateNumberOfBags());
        System.out.println(train.calculateNumberOfPassengers());
        System.out.println(train.findWagonByPassangersRange(45,100).toString());
        System.out.println(train.getWagons().toString());
        train.sortWagonsByComfort();
        System.out.println(train.getWagons().toString());
    }
}
