package com.example.javafx;

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

        Pane pane = new Pane();
        pane.setMinWidth(Region.USE_PREF_SIZE);
        pane.setMinHeight(Region.USE_PREF_SIZE);

        for (Vertex vertex : vertices) drawVertex(vertex, pane, Color.RED);

        ArrayList<Vertex> res = findClosestVertices(vertices);

        for (Vertex vertex : res) drawVertex(vertex, pane, Color.GOLD);

        vBox.getChildren().add(pane);
    }

    private ArrayList<Vertex> findClosestVertices(ArrayList<Vertex> vertices)
    {
        vertices.sort(this::compareByX);

        if(vertices.size() <= 1)
            throw new IllegalArgumentException("bad vertices, not liking bad vertices >:(");

        return new ArrayList<>(divideAndConquer(vertices));
    }

    private List<Vertex> divideAndConquer(List<Vertex> vertices)
    {
        ArrayList<Vertex> res = new ArrayList<>(2);

        if(vertices.size() <= 3)
        {
            Vertex v1 = vertices.get(0), v2 = vertices.get(1);
            double minDistance = findDistance(v1, v2);

            for(int i = 0; i < vertices.size(); i++)
            {
                for(int j = i + 1; j < vertices.size(); j++)
                {
                    double distance = findDistance(vertices.get(i), vertices.get(j));

                    if(distance < minDistance)
                    {
                        v1 = vertices.get(i);
                        v2 = vertices.get(j);
                        minDistance = distance;
                    }
                }
            }

            res.add(v1);
            res.add(v2);

            return res;
        }

        int middle = vertices.size()/2;

        List<Vertex> left = vertices.subList(0,middle);
        List<Vertex> right = vertices.subList(middle, vertices.size());

        List<Vertex> leftPair = divideAndConquer(left);
        List<Vertex> rightPair = divideAndConquer(right);

        double minDistance = Math.min(findDistance(leftPair.get(0), leftPair.get(1)),
                findDistance(rightPair.get(0), rightPair.get(1)));

        if(findDistance(leftPair.get(0), leftPair.get(1)) > findDistance(rightPair.get(0), rightPair.get(1)))
            res.addAll(rightPair);
        else
            res.addAll(leftPair);

        left.sort(this::compareByY);
        right.sort(this::compareByY);

        int j = 0;

        for(int i = 0; i < middle; i++)
        {
            while(j != rightPair.size())
            {
                double distance = findDistance(left.get(i), right.get(j));

                if(distance >= minDistance)
                {
                    if(left.get(i).y < right.get(j).y)
                        break;

                    j++;
                    continue;
                }

                res.set(0, left.get(i));
                res.set(1, right.get(j));
                minDistance = distance;
                j++;
            }
        }

        return res;
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