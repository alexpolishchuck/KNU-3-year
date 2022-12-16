package utils;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Random;

public class Phone implements Serializable {

    public Phone()
    {
        Random rand = new Random();
        id = rand.nextInt(100);
        surname = rand.ints(48, 123)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(10)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        address = rand.ints(48, 123)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(10)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        name = rand.ints(48, 123)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(10)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        time = rand.nextInt(10);
        hasUsedLongDistance = rand.nextBoolean();
    }

    public int getId() {
        return id;
    }

    public String getSurname() {
        return surname;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public int getTime() {
        return time;
    }

    public boolean HasUsedLongDistance() {
        return hasUsedLongDistance;
    }

    public String toString()
    {
        return id + " " + surname + " " + name + " " + address + " " +time + " " + hasUsedLongDistance;
    }


    public static final int maxTime = 5;
    private int id;
    private String surname;
    private String name;
    private String address;
    private int time;
    private boolean hasUsedLongDistance;
}
