package com.example.javafx.triangulation;
import com.example.javafx.Vertex;
import java.util.List;

public class VertexUtils {
    public static void sortCounterClockwise(List<Vertex> triangle)
    {
        if(triangle.size() != 3)
            throw new IllegalArgumentException("sortCounterClockwise: Wrong triangle");

        if(vectorMultiplication(triangle) < 0)
        {
            Vertex temp = triangle.get(0);
            triangle.set(0, triangle.get(1));
            triangle.set(1, temp);
        }
    }

    public static double vectorMultiplication(List<Vertex> vertices)
    {
        return Math.signum( (vertices.get(1).x - vertices.get(0).x)
                *(vertices.get(2).y - vertices.get(0).y)
                - (vertices.get(2).x - vertices.get(0).x)*(vertices.get(1).y - vertices.get(0).y));
    }

}
