package org.example;

import java.util.Comparator;

public class VertexComparator implements Comparator<Vertice>
{
    @Override
    public int compare(Vertice v1, Vertice v2) {
        if(v1.getY() < v2.getY())
            return -1;

        if(v1.getY() > v2.getY())
            return 1;

        if(v1.getX() < v1.getX())
            return -1;

        if(v1.getX() > v1.getX())
            return 1;

        return 0;
    }
}
