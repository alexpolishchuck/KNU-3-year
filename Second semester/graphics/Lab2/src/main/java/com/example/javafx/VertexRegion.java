package com.example.javafx;

import java.util.ArrayList;
import java.util.List;

public class VertexRegion {
    public VertexRegion()
    {
        vertices = new ArrayList<>();
    }
    public VertexRegion(List<Vertex> vertices)
    {
        vertices.sort((Vertex v1, Vertex v2) ->{
                return (int)Math.signum(v1.getY() - v2.getY());
        });

        this.vertices = vertices;

    }

    public List<Vertex> sortVertices()
    {
        vertices.sort((Vertex v1, Vertex v2) ->{
            return (int)Math.signum(v1.getY() - v2.getY());
        });
        return vertices;
    }
    public List<Vertex> vertices;
}
