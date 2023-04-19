package com.example.javafx;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Comparator;

public class EdgeComparator implements Comparator<Pair<Vertex,Vertex>> {

    @Override
    public int compare(Pair<Vertex,Vertex> o1, Pair<Vertex,Vertex> o2)
    {
        if(o1.getValue().getY() > o2.getValue().getY()
                && o1.getValue().getY() > o2.getKey().getY()
                && o1.getKey().getY() > o2.getValue().getY()
                && o1.getKey().getY() > o2.getKey().getY())
            return 1;

        if(o2.getValue().getY() > o1.getValue().getY()
                && o2.getValue().getY() > o1.getKey().getY()
                && o2.getKey().getY() > o1.getValue().getY()
                && o2.getKey().getY() > o1.getKey().getY())
            return -1;

        ArrayList<Vertex> vertices = new ArrayList<>();
        vertices.add(o1.getKey());
        vertices.add(o1.getValue());
        vertices.add(o2.getKey());
        vertices.add(o2.getValue());

        Vertex min = o1.getKey();
        Vertex max = o1.getKey();
        Vertex middle = null;
        VertexComparator comparator = new VertexComparator();

        for(Vertex v : vertices )
        {
            if(comparator.compare(min, v) == 1)
            {
                if(!min.equals(max))
                {
                    middle = min;
                    break;
                }
                min = v;
            }
            else if(comparator.compare(max, v) == -1)
            {
                if(!min.equals(max))
                {
                    middle = max;
                    break;
                }
                max = v;
            }
            else if(!v.equals(min) && !v.equals(max))
            {
                middle = v;
                break;
            }
        }

        if(middle == null)
            return 0;

        int x1 = (int)findX(middle.getY(), o1);
        int x2 = (int)findX(middle.getY(), o2);

        return (int)Math.signum(x1-x2);
    }

    private double findX(int y, Pair<Vertex,Vertex> v)
    {
        if(v.getKey().getY() != v.getValue().getY())
            return (y - v.getValue().getY()) * (v.getKey().getX() - v.getValue().getX())/(v.getKey().getY() - v.getValue().getY()) + v.getValue().getX();

        return (v.getKey().getX() + v.getValue().getX())/2;
    }
}
