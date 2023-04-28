package com.example.javafx;

import javafx.scene.layout.Region;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class RegionTree {

    public RegionTree()
    {
        regionVertices = new ArrayList<>();
    }
    public RegionTree(List<Vertex> vertices)
    {
        vertices.sort(this::compareByX);

        int size = vertices.size();

        regionVertices = vertices;          //is it clone
        leftX = regionVertices.get(0).x;
        rightX = regionVertices.get(regionVertices.size() - 1).x;

        if(regionVertices.size() > 1 && leftX - rightX != 0)
        {
            int middle = (rightX + leftX - 1)/2;

            ArrayList<Vertex> leftSublist = new ArrayList<>();
            ArrayList<Vertex> rightSublist = new ArrayList<>();

            for (Vertex regionVertex : regionVertices) {
                if (regionVertex.getX() <= middle)
                    leftSublist.add(regionVertex);
                else
                    rightSublist.add(regionVertex);
            }

            left = buildTree(leftSublist);
            right = buildTree(rightSublist);

            regionVertices.sort(this::compareByY);
        }


    }

    private int compareByX(Vertex v1, Vertex v2)
    {
        return (int)Math.signum(v1.getX() - v2.getX());
    }
    private int compareByY(Vertex v1, Vertex v2)
    {
        return (int)Math.signum(v1.getY() - v2.getY());
    }

    public List<Vertex> findRequestVertices(List<Vertex> request)
    {
        if(request.size() != 4)
            return null;

        request.sort(new VertexComparator());

        List<Vertex> res = findVertices(request, this);

        return removeExtraVertices(res, request);
    }

    private List<Vertex> removeExtraVertices(List<Vertex> vertices, List<Vertex> request)
    {
        if(vertices == null || vertices.size() == 0)
            return vertices;

        Vertex leftRequest = request.get(0);
        Vertex rightRequest = request.get(2);

        int left = 0;
        int right = vertices.size()-1;
        Vertex pivot = null;
        int pivotPos = -1;

        while(left != right)
        {
            int pos = (left + right)/2;
            Vertex curr = vertices.get(pos);

            if(curr.y < leftRequest.y)
                left = pos;
            else
                right = pos;

            if(curr.y <= rightRequest.y)
            {
                pivot = curr;
                pivotPos = pos;
            }

            if(curr.y == leftRequest.y)
                break;
        }

        if(pivot == null)
            return new ArrayList<>();

        left = pivotPos;

        while(pivot.y <= rightRequest.y)
        {
            pivotPos++;

            if(pivotPos == vertices.size())
                break;
            pivot = vertices.get(pivotPos);
        }

        return vertices.subList(left, pivotPos);
    }
    private List<Vertex> findVertices(List<Vertex> request, RegionTree node)
    {
        Vertex left = request.get(0);
        Vertex right = request.get(1);

        if(node == null || left.x > node.rightX || right.x < node.leftX)
            return new ArrayList<>();

        if(left.x <= node.leftX && right.x >= node.rightX)
            return node.regionVertices;

        ArrayList<Vertex> res = new ArrayList<>();

        res.addAll(findVertices(request, node.left));
        res.addAll(findVertices(request, node.right));

        return res;
    }

    private RegionTree buildTree(List<Vertex> vertexList)
    {
        RegionTree regionTree = new RegionTree();

        regionTree.leftX = vertexList.get(0).x;
        regionTree.rightX = vertexList.get(vertexList.size() - 1).x;
        regionTree.regionVertices = vertexList;

        if(vertexList.size() == 1 || regionTree.leftX - regionTree.rightX == 0)
        {
            regionTree.regionVertices.sort(regionTree::compareByY);
            return  regionTree;
        }

        int middle = (regionTree.rightX + regionTree.leftX - 1)/2;

        ArrayList<Vertex> leftSublist = new ArrayList<>();
        ArrayList<Vertex> rightSublist = new ArrayList<>();

        for (Vertex regionVertex : vertexList) {
            if (regionVertex.getX() <= middle)
                leftSublist.add(regionVertex);
            else
                rightSublist.add(regionVertex);
        }

        regionTree.left = buildTree(leftSublist);
        regionTree.right = buildTree(rightSublist);

        regionTree.regionVertices.sort(regionTree::compareByY);

        return regionTree;
    }

    public RegionTree left;
    public RegionTree right;
    private int leftX;
    private int rightX;
    public List<Vertex> regionVertices;
}
