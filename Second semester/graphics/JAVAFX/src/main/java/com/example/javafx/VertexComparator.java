package com.example.javafx;

import java.util.Comparator;

public class VertexComparator implements Comparator<Vertex>
{
    @Override
    public int compare(Vertex v1, Vertex v2) {
        if(v1.getY() < v2.getY())
            return -1;

        if(v1.getY() > v2.getY())
            return 1;

        if(v1.getX() < v2.getX())
            return -1;

        if(v1.getX() > v2.getX())
            return 1;

        return 0;
    }
}
