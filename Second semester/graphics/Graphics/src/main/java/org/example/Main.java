package org.example;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception
    {
        StackPane root = new StackPane();
        root.setPadding(new Insets(20));

        Pane pane = new Pane();
        root.getChildren().add(pane);

        Scene sc = new Scene(root, 600, 600);
        stage.setScene(sc);
        stage.show();

    }

    public static void main(String[] args)
    {
        int[][] graph = readGraph();
        ArrayList<Vertice> vertices = readVerices();

        regularizeGraph(graph, vertices);

        Application.launch(args);
    }

    private static void regularizeGraph(int[][] graph, ArrayList<Vertice> vertices)
    {
        int size = graph.length;

        vertices.sort(new VertexComparator());





    }

    private void drawGraph()
    {

    }

    private void sweepLineAlgorithm()
    {

    }
    private static int[][] readGraph()
    {
        int[][] graph = null;

        try{
            BufferedReader reader = new BufferedReader(new FileReader("src/main/java/org/example/graph.txt"));
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

    private static ArrayList<Vertice> readVerices()
    {
        ArrayList<Vertice> vertices = new ArrayList<>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader("src/main/java/org/example/vertices.txt"));
            String line = reader.readLine();
            Integer counter = 0;

            while(line != null)
            {
                String[] split = line.split(" ");

                vertices.add(new Vertice(counter, Integer.parseInt(split[0]), Integer.parseInt(split[1])));

                line = reader.readLine();
                counter++;
            }

        } catch (Exception ex) {
            System.out.println("readVerices: " + ex.getMessage());
            return null;
        }

        return vertices;
    }
}
