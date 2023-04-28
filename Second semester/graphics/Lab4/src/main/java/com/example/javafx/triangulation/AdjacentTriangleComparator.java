package com.example.javafx.triangulation;

import com.example.javafx.Vertex;
import javafx.util.Pair;

import java.util.ArrayList;

public class AdjacentTriangleComparator implements TriangleTreeComparator{
    public AdjacentTriangleComparator(TriangleTreeNode currentNode, Pair<Vertex, Vertex> edge)
    {
        this.currentNode = currentNode;
        this.edge = edge;
    }

    @Override
    public boolean isRightTriangle(TriangleTreeNode node) {
        return node.vertices.contains(edge.getKey())
                && node.vertices.contains(edge.getValue())
                && !node.equals(currentNode);
    }

    public TriangleTreeNode currentNode;
    public Pair<Vertex, Vertex> edge;
}
