package com.example.javafx.triangulation;

import com.example.javafx.Vertex;

import java.util.ArrayList;
import java.util.List;

public class BelongsToTriangleComparator implements TriangleTreeComparator{

    public BelongsToTriangleComparator(Vertex vertex)
    {
        this.vertex = vertex;
    }
    @Override
    public boolean isRightTriangle(TriangleTreeNode node) {

        List<Vertex> vertices = node.vertices;

        double d1 = vertex.locateVertexRelativelyToEdge(vertices.get(0), vertices.get(1));
        double d2 = vertex.locateVertexRelativelyToEdge(vertices.get(1), vertices.get(2));
        double d3 = vertex.locateVertexRelativelyToEdge(vertices.get(2), vertices.get(0));

        boolean has_neg = (d1 < 0) || (d2 < 0) || (d3 < 0);
        boolean has_pos = (d1 > 0) || (d2 > 0) || (d3 > 0);

        if(d1 == d2 && d2 == d3 && d3 == 0)
            return false;

        return !(has_neg && has_pos);
    }
    public Vertex vertex;
}
