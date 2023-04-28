package com.example.javafx.triangulation;

import com.example.javafx.Vertex;

import java.util.ArrayList;

public class TriangleTreeNode {

    public TriangleTreeNode()
    {}

    public TriangleTreeNode(ArrayList<Vertex> vertices)
    {
        this.vertices = vertices;
    }

    public TriangleTreeNode findTriangleTreeNode(TriangleTreeComparator comparator)
    {
        for(TriangleTreeNode node : sons)
        {
            if(comparator.isRightTriangle(node))
                return node;
        }

        return null;
    }

    public ArrayList<Vertex> vertices;
    public ArrayList<TriangleTreeNode> sons;
    public TriangleTreeNode rightLeaf;
}
