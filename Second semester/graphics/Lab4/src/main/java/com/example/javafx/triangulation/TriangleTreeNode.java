package com.example.javafx.triangulation;

import com.example.javafx.Vertex;

import java.util.ArrayList;
import java.util.List;

public class TriangleTreeNode {

    public TriangleTreeNode()
    {
    }

    public TriangleTreeNode(List<Vertex> vertices)
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

    public List<Vertex> vertices;
    public ArrayList<TriangleTreeNode> sons;
    public TriangleTreeNode rightLeaf = null;
    public TriangleTreeNode leftLeaf = null;
}
