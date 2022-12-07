package socket.socket.request;

import java.io.Serializable;

public class vegetable implements Serializable
{
    public vegetable(String cat, String name)
    {
        category = cat;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    @Override
    public String toString() {
        return "Vegetable: category: " + category + "; name: " + name + ";";
    }

    private String category;
    private String name;
}
