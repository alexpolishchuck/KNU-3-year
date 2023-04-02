package com.example.javafx;

import java.util.Comparator;

public class VertexTree {

    public void addNode(Vertex vertex)
    {
        if(root == null)
            root = new Node(vertex, 1);


    }

    private Node root = null;
    private Comparator<Vertex> comparator;
}

class Node
{
    public Node(Vertex value, int color)
    {
        this.value = value;
        this.color = color;
    }
    public Node(Vertex value)
    {
        this.value = value;
    }

    public int color = 0;
    public Node left = null;
    public Node right = null;
    public Node parent = null;
    public Vertex value;
}
