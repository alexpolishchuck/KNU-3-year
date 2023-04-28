package com.example.javafx;

public class Vertex {

    public Vertex(Integer Id, Integer x, Integer y)
    {
        this.Id = Id;
        this.x = x;
        this.y = y;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    @Override
    public boolean equals(Object v) {
        return (x == ((Vertex)v).getX() && y == ((Vertex)v).getY());
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

    public Integer Id;
    public Integer x;
    public Integer y;
}
