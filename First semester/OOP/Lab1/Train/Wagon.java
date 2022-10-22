package OOP.Lab1.Train;

public class Wagon {

    public Wagon(){

    }
    public int getMaxNumberOfPassengers(){
        return maxNumberOfPassengers;
    }

    public int getLevelOfComfort() {
        return levelOfComfort;
    }

    public int getMaxNumberOfBags() {
        return maxNumberOfBags;
    }

    protected int maxNumberOfPassengers;
    protected int maxNumberOfBags;
    protected int levelOfComfort;
}
