package OOP.Lab1.Comparators;

import OOP.Lab1.Train.Wagon;

import java.util.Comparator;

public class AscendingComparator implements Comparator<Wagon> {
    @Override
    public int compare(Wagon one, Wagon two) {
        if(one.getLevelOfComfort() > two.getLevelOfComfort())
            return 1;
        else if(one.getLevelOfComfort() < two.getLevelOfComfort())
            return -1;
        else
            return 1;

    }
}
