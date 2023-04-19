package com.example.javafx;

import javafx.util.Pair;

public class EdgeVertexUtility {

    public static int compareEdgeAndVertex(Vertex v, Pair<Vertex, Vertex> edge)
    {
        VertexComparator comparator = new VertexComparator();
        if(comparator.compare(edge.getKey(), edge.getValue()) == -1)
            edge = new Pair<>(edge.getValue(), edge.getKey());

        return (int)Math.signum((edge.getValue().getX() - edge.getKey().getX())
                * (v.getY() - edge.getKey().getY())
                - (edge.getValue().getY() - edge.getKey().getY())
                * (v.getX() - edge.getKey().getX()));
    }

}
