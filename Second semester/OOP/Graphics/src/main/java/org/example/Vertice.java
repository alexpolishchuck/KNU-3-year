package org.example;

public class Vertice {

    public Vertice(Integer Id, Integer x, Integer y)
    {
        this.Id = Id;
        this.x = x;
        this.y = y;
    }

    public Integer getId() {
        return Id;
    }

    public Integer getX() {
        return x;
    }

    public Integer getY() {
        return y;
    }

    private Integer Id;
    private Integer x;
    private Integer y;
}
