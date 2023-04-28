package com.example.javafx;

import javafx.util.Pair;

import java.util.ArrayList;

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

    public double locateVertexRelativelyToEdge(Vertex v2, Vertex v3)
    {
        ArrayList<Vertex> edge = new ArrayList<>();
        edge.add(v2);
        edge.add(v3);
        edge.sort((Vertex v0, Vertex v1)->{return (int)Math.signum(v0.Id - v1.Id);});

        Pair<Double, Double> vector1 = null;
        Pair<Double, Double> vector2 = null;

        if(edge.get(0).Id == -1)
        {
            vector1 = new Pair<>(-1d, 1d);
            vector2 = vector1;
        }
        else if(edge.get(0).Id == -2)
        {
            vector1 = new Pair<>(1d,-1d);
            vector2 = vector1;
        }
        else
        {
            vector1 = new Pair<>((double)(edge.get(1).x - edge.get(0).x), (double)(edge.get(1).y - edge.get(0).y));
            vector2 = new Pair<>((double)(x - edge.get(0).x), (double)(y - edge.get(0).y));
        }

        return Math.signum(vector2.getKey() * vector1.getValue() - vector2.getKey() * vector2.getValue());
    }

    public Integer Id;
    public Integer x;
    public Integer y;
}
