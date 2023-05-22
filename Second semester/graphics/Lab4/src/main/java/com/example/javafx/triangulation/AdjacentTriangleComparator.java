package com.example.javafx.triangulation;

import com.example.javafx.Vertex;
import javafx.util.Pair;

import java.util.ArrayList;

public class AdjacentTriangleComparator implements TriangleTreeComparator{
    public AdjacentTriangleComparator(TriangleTreeNode currentNode, Pair<Vertex, Vertex> edge)
    {
        this.currentNode = currentNode;
        this.edge = edge;
        comparator = new BelongsToTriangleComparator(edge.getKey());
    }

    @Override
    public boolean isRightTriangle(TriangleTreeNode node) {

        boolean res = comparator.isRightTriangle(node);
        comparator.vertex = edge.getValue();
        res = res || comparator.isRightTriangle(node);
        comparator.vertex = edge.getKey();

        if(node.sons != null && res)
            return true;

        return node.vertices.contains(edge.getKey())
                && node.vertices.contains(edge.getValue())
                && !node.vertices.containsAll(currentNode.vertices);
    }

    public TriangleTreeNode currentNode;
    public Pair<Vertex, Vertex> edge;

    private BelongsToTriangleComparator comparator;
}
