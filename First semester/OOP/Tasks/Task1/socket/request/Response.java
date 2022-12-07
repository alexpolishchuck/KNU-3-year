package socket.socket.request;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public class Response implements Serializable {

    public Response()
    {}
    public Response( ArrayList<vegetable>vegs )
    {
        if(vegs==null)
            vegies = new ArrayList<>();
        else
            vegies = vegs;
    }
    public void addVegetable(vegetable veg)
    {
        if(veg == null)
            return;
        vegies.add(veg);
    }

    public ArrayList<vegetable> getVegies() {
        return vegies;
    }

    private ArrayList<vegetable> vegies;
}
