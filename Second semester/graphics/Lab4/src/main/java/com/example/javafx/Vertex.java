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
        return (x == ((Vertex)v).getX() && y == ((Vertex)v).getY() && Id == ((Vertex) v).Id);
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

        Pair<Double, Double> vector1 = new Pair<>((double)(edge.get(1).x - edge.get(0).x), (double)(edge.get(1).y - edge.get(0).y));
        Pair<Double, Double> vector2 = new Pair<>((double)(x - edge.get(0).x), (double)(y - edge.get(0).y));

        return Math.signum(vector2.getKey() * vector1.getValue() - vector1.getKey() * vector2.getValue());
    }

    public Integer Id;
    public Integer x;
    public Integer y;
}
