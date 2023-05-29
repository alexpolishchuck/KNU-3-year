package com.example.javafx;

import com.example.javafx.triangulation.*;
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
import javafx.scene.shape.Polygon;
import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
import java.util.stream.Collectors;

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

        TriangleTreeNode root = DelaunayTriangulation(vertices);

        Pane pane = new Pane();
        Color color = Color.CORNFLOWERBLUE;

        drawTriangulation(root, pane, color);

        vBox.getChildren().add(pane);
    }

    private void drawTriangulation(TriangleTreeNode node, Pane pane, Color color)
    {
        if(node.sons != null)
        {
            for(TriangleTreeNode child : node.sons)
                drawTriangulation(child, pane, color);

            return;
        }

        ArrayList<Vertex> vertices = new ArrayList<>(node.vertices);

       for(int i = 0; i < vertices.size(); i++)
            if(vertices.get(i).Id < 0)
            {
                vertices.remove(i);
                i--;
            }

        int size = vertices.size();

        for(int i = 0; i < size; i++)
            drawEdge(vertices.get(i), vertices.get((i + 1) % size), pane, color);

        for(Vertex vertex : vertices)
            drawVertex(vertex, pane, color);
    }

    //legalize edge
    //find highest
    //

    private TriangleTreeNode DelaunayTriangulation(ArrayList<Vertex> vertices)
    {
        TriangleTreeNode root = createInitialTriangle(vertices);

        checkIfAllVerticesAreInside(vertices, root);

        Random random = new Random();

        while(vertices.size() > 0)
        {
            int pos = random.nextInt(vertices.size());
            //     int pos = 0;

            Vertex vertex = vertices.remove(pos);
            ArrayList<TriangleTreeNode> triangles = new ArrayList<>();
            locateSuitableTriangles(triangles, root, new BelongsToTriangleComparator(vertex));

            if(triangles.size() == 0 || triangles.size() > 2)
                throw new RuntimeException("Wrong input");

            for(TriangleTreeNode node : triangles)
            {
                node.sons = new ArrayList<>(3);

                for(int i = 0; i < 3; i++)
                {
                    TriangleTreeNode child  = new TriangleTreeNode(new ArrayList<>(node.vertices));
                    child.vertices.remove(i);

                    if(vertex.locateVertexRelativelyToEdge(child.vertices.get(0), child.vertices.get(1)) != 0)
                    {
                        child.vertices.add(vertex);
                        node.sons.add(child);
                    }
                }
            }

            for(TriangleTreeNode node : triangles)
            {
                for(TriangleTreeNode child : node.sons) // fix
                {
                    // we know those are correct vertices because we added new vertex last
                    legalizeEdge(vertex,
                            new Pair<>(child.vertices.get(0), child.vertices.get(1)),
                            child,
                            root);
                }
            }
        }//while

        return root;
    }
    private void checkIfAllVerticesAreInside(List<Vertex> vertices, TriangleTreeNode root)
    {
        for(Vertex v : vertices)
        {
            BelongsToTriangleComparator comparator = new BelongsToTriangleComparator(v);

            System.out.println("ID: " + v.Id + " ; " + comparator.isRightTriangle(root));
        }
    }

    private TriangleTreeNode createInitialTriangle(ArrayList<Vertex> vertices)
    {
        int minX = vertices.get(0).x, minY = vertices.get(0).y;
        int maxX = vertices.get(0).x, maxY = vertices.get(0).y;

        for(Vertex v : vertices)
        {
            if(v.x > maxX)
                maxX = v.x;
            else if(v.x < minX)
                minX = v.x;

            if(v.y > maxY)
                maxY = v.y;
            else if(v.y <= minY)
                minY = v.y;
        }

        int dx = (maxX - minX) * 10,
                dy = (maxY - minY) * 10;

        if(dx == 0)
            dx = 10;

        if(dy == 0)
            dy = 10;

        return new TriangleTreeNode(new ArrayList(Arrays.asList(
                new Vertex(-1, minX - dx, minY - dy * 10),
                new Vertex(-2, minX - dx, maxY + dy),
                new Vertex(-3, maxX + dx * 10, maxY + dy))
        ));
    }


    private void legalizeEdge(Vertex vertex,
                              Pair<Vertex,Vertex> edge,
                              TriangleTreeNode node,
                              TriangleTreeNode root)
    {
        ArrayList<TriangleTreeNode> triangles = new ArrayList<>();

        locateSuitableTriangles(triangles, root, new AdjacentTriangleComparator(node, edge));

        if(triangles.size() == 0)
            return;

        TriangleTreeNode adjacentTriangle = triangles.get(0);

        Vertex vertexToCheck = null;

        for(Vertex v : adjacentTriangle.vertices)
        {
            if(!v.equals(edge.getValue()) && !v.equals(edge.getKey()))
            {
                vertexToCheck = v;
                break;
            }
        }

        if(isEdgeLegal(vertex, edge, node, vertexToCheck))
            return;

        List<TriangleTreeNode> newTriangles = new ArrayList(Arrays.asList(
                new TriangleTreeNode(new ArrayList(Arrays.asList(vertex, vertexToCheck, edge.getKey()))),
                new TriangleTreeNode(new ArrayList(Arrays.asList(vertex, vertexToCheck, edge.getValue())))));

        node.sons = new ArrayList<>(newTriangles);
        adjacentTriangle.sons = node.sons;

        //we know the order in which triangles were saved, thus we know which end of the edge to use
        legalizeEdge(vertex,
                new Pair<>(edge.getKey(), vertexToCheck),
                newTriangles.get(0),
                root);
        legalizeEdge(vertex,
                new Pair<>(edge.getValue(), vertexToCheck),
                newTriangles.get(1),
                root);
        //flip
    }

    private boolean isEdgeLegal(
            Vertex vertex,
            Pair<Vertex,Vertex> edge,
            TriangleTreeNode triangle,
            Vertex vertexToCheck) //  is lexicographically correct ??? no.
    {
        //lexicographical case
        if(edge.getKey().Id < 0 && edge.getValue().Id < 0)
            return true;

        //normal case
        VertexUtils.sortCounterClockwise(triangle.vertices);

        ArrayList<Pair<Integer, Integer>> vectors = new ArrayList<>();

        for(Vertex v : triangle.vertices)
        {
            vectors.add(createVector(v, vertexToCheck));
        }

        return ((Math.pow(vectors.get(0).getKey(), 2) + (Math.pow(vectors.get(0).getValue(), 2)))
                * (vectors.get(1).getKey()*vectors.get(2).getValue() - vectors.get(2).getKey()*vectors.get(1).getValue())
                - (Math.pow(vectors.get(1).getKey(), 2) + (Math.pow(vectors.get(1).getValue(), 2)))
                * (vectors.get(0).getKey()*vectors.get(2).getValue()-vectors.get(2).getKey()*vectors.get(0).getValue())
                + (Math.pow(vectors.get(2).getKey(), 2) + (Math.pow(vectors.get(2).getValue(), 2)))
                * (vectors.get(0).getKey()*vectors.get(1).getValue() - vectors.get(1).getKey()*vectors.get(0).getValue())
        ) < 0;
    }
    private Pair<Integer, Integer> createVector(Vertex v1, Vertex v2)
    {
        return new Pair<>(v1.x - v2.x, v1.y - v2.y);
    }

    private void locateSuitableTriangles(
            ArrayList<TriangleTreeNode> res,
            TriangleTreeNode currentNode,
            TriangleTreeComparator comparator)
    {
        if(!comparator.isRightTriangle(currentNode))
            return;

        if(currentNode.sons == null || currentNode.sons.size() == 0)
        {
            res.add(currentNode);
            return;
        }

        for(TriangleTreeNode node : currentNode.sons)
        {
                locateSuitableTriangles(res, node, comparator);
        }
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