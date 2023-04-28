package com.example.javafx;

import com.example.javafx.triangulation.AdjacentTriangleComparator;
import com.example.javafx.triangulation.BelongsToTriangleComparator;
import com.example.javafx.triangulation.TriangleTreeComparator;
import com.example.javafx.triangulation.TriangleTreeNode;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
public class HelloController {
    @FXML
    private Button startButton;
    @FXML
   // private StackPane stackPane;
    private ScrollPane scrollPane;
    @FXML
    private VBox vBox;
    @FXML
    protected void onHelloButtonClick() {
        //bloody hell

        ArrayList<Vertex> vertices = readVertices(folder + "vertices.txt");

        int[][] graph = new int[vertices.size()][vertices.size()];


    }

    //legalize edge
    //find highest
    //

    private TriangleTreeNode DelaunayTriangulation(ArrayList<Vertex> vertices)
    {
        vertices.sort(this::compareByY);

        TriangleTreeNode root = new TriangleTreeNode();

        root.vertices = new ArrayList<>(3);
        root.vertices.add(vertices.get(vertices.size()-1));
        root.vertices.add(new Vertex(-1,-1,-1));
        root.vertices.add(new Vertex(-2,-2,-2));

        vertices.remove(vertices.size()-1);

        Random random = new Random();

        while(vertices.size() > 0)
        {
            int pos = random.nextInt(vertices.size());

            Vertex vertex = vertices.get(pos);
            ArrayList<TriangleTreeNode> triangles = new ArrayList<>();
            locateSuitableTriangles(triangles, root, new BelongsToTriangleComparator(vertex));

            if(triangles.size() == 0 || triangles.size() > 3)
                throw new RuntimeException("Wrong input");

            triangles.get(0).sons = new ArrayList<>(3);

            for(int i = 0; i < 3; i++)
            {
               TriangleTreeNode node = new TriangleTreeNode(new ArrayList<>(triangles.get(0).vertices));

               node.vertices.remove(i);

               if(vertex.locateVertexRelativelyToEdge(node.vertices.get(0), node.vertices.get(1)) != 0)
               {
                   node.vertices.add(vertex);
                   triangles.get(0).sons.add(node);
               }
            }

            //for all three/two edges:
            //is legal
            // not legal, flip
        }//while

        return root;
    }
    private void legalizeEdge(Vertex vertex, Pair<Vertex,Vertex> edge, TriangleTreeNode node)
    {
        ArrayList<TriangleTreeNode> triangles = new ArrayList<>();
        locateSuitableTriangles(triangles, node, new AdjacentTriangleComparator(node, edge));

        if(triangles.size() == 0)
            return;

        //finish else

    }

    private boolean isEdgeLegal(TriangleTreeNode node)
    {
        if(vectorMultiplication(node.vertices) < 0)
        {
            Vertex temp = node.vertices.get(0);
            node.vertices.set(0, node.vertices.get(1));
            node.vertices.set(1, temp);
        }
        double ax_ = ax-dx;
        double ay_ = ay-dy;
        double bx_ = bx-dx;
        double by_ = by-dy;
        double cx_ = cx-dx;
        double cy_ = cy-dy;

        return (
                (ax_*ax_ + ay_*ay_) * (bx_*cy_-cx_*by_) -
                        (bx_*bx_ + by_*by_) * (ax_*cy_-cx_*ay_) +
                        (cx_*cx_ + cy_*cy_) * (ax_*by_-bx_*ay_)
        ) > 0;
    }
    private double vectorMultiplication(ArrayList<Vertex> vertices)
    {
        return Math.signum( (vertices.get(1).x - vertices.get(0).x)
                *(vertices.get(2).y - vertices.get(0).y)
                - (vertices.get(2).x - vertices.get(0).x)*(vertices.get(1).y - vertices.get(0).y));
    }
    private void locateSuitableTriangles(
            ArrayList<TriangleTreeNode> res,
            TriangleTreeNode currentNode,
            TriangleTreeComparator comparator)
    {
        if(currentNode.sons.size() == 0)
            res.add(currentNode);

        for(TriangleTreeNode node : currentNode.sons)
        {
            if(comparator.isRightTriangle(node))
                locateSuitableTriangles(res, node, comparator);
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

    private double findDistance(Vertex v1, Vertex v2)
    {
        return Math.sqrt(Math.pow((v1.getX() - v2.getX()),2) + Math.pow((v1.getY() - v2.getY()),2));
    }

    private void drawEdge(Vertex vertex1, Vertex vertex2, Pane pane, Color color)
    {
        double vertexSize = 2 * vertexRadius;

        Line line = new Line();
        line.setStartX((vertex1.getX() + 0.5 ) * vertexSize + currentPaneOffsetX * vertexSize);
        line.setEndX((vertex2.getX() + 0.5 ) * vertexSize + currentPaneOffsetX * vertexSize);
        line.setStartY( (-1) * (vertex1.getY() - 0.5) * vertexSize + currentPaneOffsetY * vertexSize);
        line.setEndY( (-1) * (vertex2.getY() - 0.5) * vertexSize + currentPaneOffsetY * vertexSize);

        if(color != null)
            line.setStroke(color);

        pane.getChildren().add(line);
    }

    private void drawVertex(Vertex vertex, Pane pane, Paint paint)
    {
        int paneSize = 2 * vertexRadius;

        double maxWidth = 0;
        double maxHeight = 0;

        StackPane dotPane = new StackPane();

        Circle dot = new Circle();

        Label txt = new Label(Integer.toString(vertex.getId()));
        txt.setStyle("-fx-font-size:18px;-fx-font-weight:bold;");

        dot.setRadius(vertexRadius);
        dot.setStyle("-fx-stroke-width:2px;-fx-stroke:black;");
        dot.setFill(paint);

        dotPane.getChildren().addAll(dot,txt);
        dotPane.setPrefSize(paneSize, paneSize);
        dotPane.setMaxSize(paneSize, paneSize);
        dotPane.setMinSize(paneSize, paneSize);

        if(vertex.getX() < 0 && Math.abs(vertex.getX()) > currentPaneOffsetX)
        {
            double diff = Math.abs(vertex.getX()) - currentPaneOffsetX;
            currentPaneOffsetX = Math.abs(vertex.getX());

            ObservableList<Node> children = pane.getChildren();
            ListIterator<Node> iterator = children.listIterator();

            while (iterator.hasNext())
            {
                Node node = (Node)iterator.next();
                double width = node.getLayoutX() + diff * paneSize;

                if(width > maxWidth)
                    maxWidth = width;

                node.setLayoutX(width);
            }
        }
        else
        {
            maxWidth = (currentPaneOffsetX + vertex.getX()) * paneSize;
            dotPane.setLayoutX(maxWidth);
        }

        if(vertex.getY() > 0 && vertex.getY() > currentPaneOffsetY)
        {
            double diff = vertex.getY() - currentPaneOffsetY;
            currentPaneOffsetY = vertex.getY();

            ObservableList<Node> children = pane.getChildren();
            ListIterator<Node> iterator = children.listIterator();

            while (iterator.hasNext())
            {
                Node node = (Node)iterator.next();
                double height = node.getLayoutY() + diff * paneSize;

                if(height > maxHeight)
                    maxHeight = height;

                node.setLayoutY(height);
            }
        }
        else
        {
            maxHeight = ( currentPaneOffsetY - vertex.getY()) * paneSize;
            dotPane.setLayoutY(maxHeight);
        }

        if(maxHeight > pane.getMinHeight())
            pane.setMinHeight(maxHeight + paneSize);
        if(maxWidth > pane.getMinWidth())
            pane.setMinWidth(maxWidth + paneSize);

        pane.getChildren().add(dotPane);
    }
    private ArrayList<Vertex> readVertices(String path)
    {
        ArrayList<Vertex> vertices = new ArrayList<>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));

            String line = reader.readLine();
            Integer counter = 0;

            while(line != null)
            {
                String[] split = line.split(" ");

                vertices.add(new Vertex(counter, Integer.parseInt(split[0]), Integer.parseInt(split[1])));

                line = reader.readLine();
                counter++;
            }

        } catch (Exception ex) {
            System.out.println("readVertices: " + ex.getMessage());
            return null;
        }

        return vertices;
    }
    private int vertexRadius = 20;
    private int currentPaneOffsetX = 0;
    private int currentPaneOffsetY = 0;

    private String folder = "src/main/java/com/example/javafx/";
}