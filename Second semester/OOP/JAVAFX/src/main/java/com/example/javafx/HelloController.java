package com.example.javafx;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
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
        // ADD NEW PANE WITH FIXED SIZE FOR SCROLLING EFFECT

       int[][] graph = readGraph();
       ArrayList<Vertex> vertices = readVertices();
       Vertex target = new Vertex(-1, 8, 6);

       drawGraph(graph, vertices, target);

       regularizeGraph(graph, vertices, true);
       regularizeGraph(graph, vertices, false);

       balanceEdges(graph, vertices, false);
       balanceEdges(graph, vertices, true);

       Pair<Pair<Vertex, Vertex>, Pair<Vertex, Vertex>> res = sweepLineAlgorithm(graph, vertices, target);


       drawGraph(graph, vertices, target);

       highlightEdge(res.getKey());
       highlightEdge(res.getValue());
    }

    private void drawGraph(int[][] graph, ArrayList<Vertex> vertices, Vertex target)
    {
        currentPaneOffsetY = 0;
        currentPaneOffsetX = 0;

        Pane pane = new Pane();
        pane.setMinWidth(Region.USE_PREF_SIZE);
        pane.setMinHeight(Region.USE_PREF_SIZE);

        vertices.sort((Vertex v1,Vertex v2) ->{return (int)Math.signum(v1.getId() - v2.getId());});

        int size = graph.length;

        for(int i=0; i < size; i++)
        {
            for(int j = i + 1; j< size; j++)
                if(graph[i][j] >= 1)
                    drawEdge(vertices.get(i), vertices.get(j), pane, Color.BLACK);

        }

        for(Vertex v : vertices)
            drawVertex(v, pane);

        if(target != null)
            drawVertex(target, pane);

        vBox.getChildren().add(pane);
    }

    private Pair<Pair<Vertex, Vertex>,Pair<Vertex, Vertex>> sweepLineAlgorithm(
            int[][] graph, ArrayList<Vertex> vertices, Vertex target)
    {
        int size = vertices.size();
        VertexComparator vertexComparator = new VertexComparator();
        vertices.sort(vertexComparator);

        int[][] graphClone = new int[size][];
        for(int i = 0; i < size; i++)
        {
            graphClone[i] = graph[i].clone();
        }

        TreeMap<Vertex, TreeSet<Vertex>> adjacentVertices = findAdjacentVertices(graphClone, vertices);

        ArrayList<TreeMap<Vertex,Vertex>> chains = new ArrayList<>();
        TreeMap<Vertex,Vertex> chain = new TreeMap<>(vertexComparator);

        Vertex firstVertex = vertices.get(0);

        //building chains
       for(Vertex v : adjacentVertices.get(firstVertex))
       {
           chain.put(firstVertex,v);
           while(graphClone[v.getId()][firstVertex.getId()] > 0)
           {
               graphClone[v.getId()][firstVertex.getId()]--;
               graphClone[firstVertex.getId()][v.getId()]--;

               buildChainStep(v, adjacentVertices, chain, graphClone);

               chains.add(chain);
               chain = new TreeMap<>(vertexComparator);
               chain.put(firstVertex,v);
           }

           chain = new TreeMap<>(vertexComparator);
       }

       //locating target

        if(target.getY() > vertices.get(size - 1).getY()
                || target.getY() < vertices.get(0).getY())
            return null;

        Pair<Vertex, Vertex> leftEdge = null;
        Pair<Vertex, Vertex> rightEdge = null;

        //binary search
        int leftPos = 0;
        int rightPos = chains.size() - 1;

        do {
            int middlePos = (leftPos + rightPos)/2;
            TreeMap<Vertex, Vertex> ch = chains.get(middlePos);

            //discrimination
            Pair<Vertex, Vertex> edge = vertexDisrimination(ch, target);

            if(EdgeVertexUtility.compareEdgeAndVertex(target, edge) == 1)
            {
                leftEdge = edge;
                leftPos = middlePos;
            }
            else
            {
                rightEdge = edge;
                rightPos = middlePos;
            }
        } while(rightPos - leftPos > 1);

        if(leftEdge == null)
        {
            leftEdge = vertexDisrimination(chains.get(leftPos), target);
            if(EdgeVertexUtility.compareEdgeAndVertex(target, leftEdge) == -1)
                rightEdge = leftEdge;
        }
        if(rightEdge == null)
        {
            rightEdge = vertexDisrimination(chains.get(rightPos), target);
            if(EdgeVertexUtility.compareEdgeAndVertex(target, rightEdge) == 1)
                leftEdge = rightEdge;
        }

        return new Pair<>(leftEdge, rightEdge);
    }

    private Pair<Vertex, Vertex> vertexDisrimination(
            TreeMap<Vertex, Vertex> ch,
            Vertex target)
    {
        VertexComparator vertexComparator = new VertexComparator();
        ArrayList<Vertex> keys = new ArrayList<>(ch.keySet());
        int chainSize = keys.size();

        Vertex lowerVertex = keys.get(0);
        Vertex upperVertex = keys.get(chainSize - 1);

        int leftVertexPos = 0;
        int rightVertexPos = chainSize - 1;

        do                               //binary search
        {
            int middleVertexPos = (leftVertexPos + rightVertexPos)/2;

            Vertex v = keys.get(middleVertexPos);

            if (vertexComparator.compare(target, v) > 0)
            {
                lowerVertex = v;
                leftVertexPos = middleVertexPos;
            }
            else
            {
                upperVertex = v;
                rightVertexPos = middleVertexPos;
            }
        } while (rightVertexPos - leftVertexPos > 1);

        return new Pair<>(lowerVertex, upperVertex);
    }


    private void highlightEdge(Pair<Vertex, Vertex> edge)
    {
        int size = ((VBox)scrollPane.getContent()).getChildren().size();
        Pane pane = (Pane)(((VBox)scrollPane.getContent()).getChildren().get(size - 1));

        if(edge != null)
        {
            drawEdge(edge.getKey(), edge.getValue(), pane, Color.BLUE);
        }
    }


    public void buildChainStep(Vertex currentVertex,
            TreeMap<Vertex, TreeSet<Vertex>> adjacentVertices, TreeMap<Vertex,Vertex> chain, int[][] graph)
    {
        for(Vertex v : adjacentVertices.get(currentVertex))
            if(graph[v.getId()][currentVertex.getId()] > 0)
            {
                graph[v.getId()][currentVertex.getId()]--;
                graph[currentVertex.getId()][v.getId()]--;

                chain.put(currentVertex,v);
                buildChainStep(v, adjacentVertices, chain, graph);
                return;
            }
        chain.put(currentVertex,null);
    }
    private TreeMap<Vertex, TreeSet<Vertex>> findAdjacentVertices(int[][] graph, ArrayList<Vertex> vertices)
    {
        int size = vertices.size();
        VertexComparator comparator = new VertexComparator();
        TreeMap<Vertex, TreeSet<Vertex>> res = new TreeMap<>(comparator);

        for(int i = 0; i < size; i++)
        {
            Vertex currentVertex = vertices.get(i);

            TreeSet<Vertex> outcomingVertices = new TreeSet<>((Vertex v1, Vertex v2) ->{
              if(v1.getX() > v2.getX())
                  return 1;
              if(v1.getX() < v2.getX())
                  return -1;
              if(v1.getX() == v2.getX())
              {
                  if(v1.getY() < v2.getY())
                      return -1;
                  if(v1.getY() > v2.getY())
                      return -1;
              }
              return 0;
            });

            for(int j = i + 1; j < size; j++)
            {
                Vertex v = vertices.get(j);

                if(graph[currentVertex.getId()][v.getId()] > 0
                        && comparator.compare(currentVertex, v) == -1)
                    outcomingVertices.add(v);
            }

            res.put(currentVertex, outcomingVertices);
        }

        return res;
    }
    private void balanceEdges(int[][] graph, ArrayList<Vertex> vertices, boolean isDescending)
    {
        int size = vertices.size();
        VertexComparator vertexComparator = new VertexComparator();

        vertices.sort((Vertex v1, Vertex v2) ->{
            return (int)Math.signum(v1.getId() - v2.getId());
        });
        ArrayList<Vertex> verticesSortedById = (ArrayList<Vertex>)vertices.clone();

        if(isDescending)
            vertices.sort(vertexComparator);
        else
            vertices.sort(vertexComparator.reversed());

        for(int i = size - 2; i > 0; i--)
        {
            Vertex currentVertex = vertices.get(i);
            int id = currentVertex.getId();

            int outcomingSum = 0;
            int incomingSum = 0;
            Vertex leftmostVertex = null;

            for(int j = 0; j < size; j++)
            {

                if(graph[id][j] > 0)
                {
                    Vertex v = verticesSortedById.get(j);

                    if(vertexComparator.compare(currentVertex, v) == 1)
                    {
                        incomingSum += graph[id][j];

                        if(isDescending && (leftmostVertex == null || v.getX() < leftmostVertex.getX()))
                            leftmostVertex = v;
                    }
                    else
                    {
                        outcomingSum += graph[id][j];

                        if(!isDescending && (leftmostVertex == null || v.getX() < leftmostVertex.getX()))
                            leftmostVertex = v;
                    }
                }
            }

            if(!isDescending && incomingSum > outcomingSum)
            {
                int leftmostId = leftmostVertex.getId();
                int sum = incomingSum - outcomingSum + 1;

                graph[leftmostId][id] = sum;
                graph[id][leftmostId] = sum;
            }
            else if(isDescending && incomingSum < outcomingSum)
            {
                int leftmostId = leftmostVertex.getId();
                int sum = outcomingSum - incomingSum + graph[leftmostId][id];

                graph[leftmostId][id] = sum;
                graph[id][leftmostId] = sum;
            }

        }
    }

    private void regularizeGraph(int[][] graph, ArrayList<Vertex> vertices, boolean isDescending)
    {
        int size = vertices.size();
        VertexComparator vertexComparator = new VertexComparator();

        if(isDescending)
            vertices.sort(vertexComparator);
        else
            vertices.sort(vertexComparator.reversed());

        ArrayList<Pair<Vertex,Vertex>> status = new ArrayList<>();

        boolean[] isVisited = new boolean[size];

        for(int i = size - 1; i >= 0; i--)
        {
            Vertex currentVertex = vertices.get(i);

            if(!isVisited[currentVertex.getId()])
            {
                for (int j = 0; j < status.size(); j++)
                {
                    Pair<Vertex, Vertex> edge = status.get(j);

                    boolean shouldRemoveEdge;

                    if(isDescending)
                        shouldRemoveEdge = edge.getKey().getY() > currentVertex.getY()
                                && edge.getValue().getY() > currentVertex.getY();
                    else
                        shouldRemoveEdge = edge.getKey().getY() < currentVertex.getY()
                                && edge.getValue().getY() < currentVertex.getY();

                    if (shouldRemoveEdge)
                    {
                        status.remove(edge);
                        j--;
                    }
                }

                status.addAll(addCrossedEdges(vertices, graph, i, isVisited));
            }

            if(shouldVertexBeRegularized(i, graph, vertices)
                    || i == 0
                    || i == size - 1)
            {
                continue;
            }

            Pair<Vertex, Vertex> leftEdge = null;
            Pair<Vertex, Vertex> rightEdge = null;

            status.sort(new EdgeComparator());

            for(Pair<Vertex, Vertex> edge : status)
            {
                if(locateVertex(edge, currentVertex) == 1
                        && !edge.getValue().equals(currentVertex)
                        && !edge.getKey().equals(currentVertex))
                {
                    leftEdge = edge;
                }
                else if(locateVertex(edge, currentVertex) == -1
                        && !edge.getValue().equals(currentVertex)
                        && !edge.getKey().equals(currentVertex))
                {
                    rightEdge = edge;
                    break;
                }
            }

            Vertex closestVertex = null;
            double minDistance = Double.MAX_VALUE;

            for(int j = i + 1; j < size; j++ )
            {
                Vertex v = vertices.get(j);

                if((leftEdge == null || locateVertex(leftEdge, v) >= 0)
                        && (rightEdge == null || locateVertex(rightEdge, v) <= 0))
                {
                    double dist = findDistance(v, currentVertex);

                    if(minDistance > dist)
                    {
                        minDistance = dist;
                        closestVertex = v;
                    }
                }
            }

            if(closestVertex != null)
            {
                graph[closestVertex.getId()][currentVertex.getId()] = 1;
                graph[currentVertex.getId()][closestVertex.getId()] = 1;
            }
        }//for i
    }

    private double findDistance(Vertex v1, Vertex v2)
    {
        return Math.sqrt(Math.pow((v1.getX() - v2.getX()),2) + Math.pow((v1.getY() - v2.getY()),2));
    }

    private int locateVertex(Pair<Vertex, Vertex> edge, Vertex vertex)
    {
        Vertex v1 = edge.getKey();
        Vertex v2 = edge.getValue();

        VertexComparator comparator = new VertexComparator();

        if(comparator.compare(v1, v2) == -1)
        {
            v1 = edge.getValue();
            v2 = edge.getKey();
        }

        return (int)Math.signum((v2.getX() - v1.getX())
                * (vertex.getY() - v1.getY())
                - (v2.getY() - v1.getY())
                * (vertex.getX() - v1.getX()));
    }
    private boolean shouldVertexBeRegularized(int pos, int[][] graph, ArrayList<Vertex> vertices)
    {
        Vertex currentVertex = vertices.get(pos);
        int id = currentVertex.getId();
        int size = vertices.size();

        for(int i = pos + 1; i < size; i++)
        {
            if(graph[id][vertices.get(i).getId()] == 1)
               return true;
        }

        return false;
    }

    private ArrayList<Pair<Vertex,Vertex>> addCrossedEdges(
            ArrayList<Vertex> vertices,
            int[][] graph,
            int pos,
            boolean[] isVisited)
    {
        Vertex currentVertex = vertices.get(pos);
        int id = currentVertex.getId();
        ArrayList<Pair<Vertex,Vertex>> res = new ArrayList<>();

        for(int j = pos - 1; j >= 0; j--)                        //wrong for from down to up
        {
            Vertex v = vertices.get(j);

            if (graph[id][v.getId()] == 1)
                res.add(new Pair<>(v, currentVertex));

            if(v.getY() == currentVertex.getY())
                res.addAll(addCrossedEdges(vertices, graph, j, isVisited));
        }

        isVisited[id] = true;

        return res;
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

    private void drawVertex(Vertex vertex, Pane pane)
    {
        int paneSize = 2 * vertexRadius;

        double maxWidth = 0;
        double maxHeight = 0;

        StackPane dotPane = new StackPane();

        Circle dot = new Circle();

        Label txt = new Label(Integer.toString(vertex.getId()));
        txt.setStyle("-fx-font-size:18px;-fx-font-weight:bold;");

        dot.setRadius(vertexRadius);
        dot.setStyle("-fx-fill:" + "red" + ";-fx-stroke-width:2px;-fx-stroke:black;");

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
    private int[][] readGraph()
    {
        int[][] graph = null;

        try{
            BufferedReader reader = new BufferedReader(new FileReader(folder + "graph.txt"));
            String line = reader.readLine();
            Integer counter = 0;

            while(line != null)
            {
                String[] edges = line.split(" ");
                int len = edges.length;

                if(graph == null)
                    graph = new int[len][len];

                for(int i=0; i<len; i++)
                {
                    if(edges[i].compareTo("0") != 0)
                        graph[counter][i] = 1;
                }

                line = reader.readLine();
                counter++;
            }

        } catch (Exception ex)
        {
            System.out.println("readGraph: " + ex.getMessage());
            return null;
        }

        return graph;
    }

    private ArrayList<Vertex> readVertices()
    {
        ArrayList<Vertex> vertices = new ArrayList<>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(folder + "vertices.txt"));

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
            System.out.println("readVerices: " + ex.getMessage());
            return null;
        }

        return vertices;
    }
    private int vertexRadius = 20;
    private int currentPaneOffsetX = 0;
    private int currentPaneOffsetY = 0;

    private String folder = "src/main/java/com/example/javafx/testData/1/";
}