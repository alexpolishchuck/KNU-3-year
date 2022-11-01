package NumericAnalysis.Lab3;


import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Formatter;
import java.util.Objects;
import java.util.Random;

class manager
{
    public manager()
    {


    }
    public void powerIteration()
    {
        graph = generateGraph(SIZE);
        double[][] matrix = generateRangMatrix(graph);
        double[]res = new double[SIZE];
        for(int i=0 ; i<SIZE; i++)
            res[i]=1;
        double[]prev = new double[SIZE];
        do {
            prev = res.clone();
            double[][] mult = multiplyMatrices(matrix, res);
            for(int i=0; i<SIZE; i++)
                res[i] = mult[i][0];
        }while(findNorm(findDifference(res,prev)) >= precision);



      System.out.println("Graph:");
      for(int i= 0 ;i <SIZE; i++)
          System.out.println(Arrays.toString(graph[i]));
        System.out.println("Rang matrix:");
        for(int i= 0 ;i <SIZE; i++)
            System.out.println(Arrays.toString(matrix[i]));
        System.out.println("RES:");
            System.out.println(Arrays.toString(res));
            test(matrix,res);
    }
    public double[] findDifference(double[] a, double[] b)
    {
        if(a == null || b == null || a.length != b.length)
            throw new IllegalArgumentException("Illegal vectors");
        int len = a.length;
        double[] res = new double[len];
        for(int i=0; i<len; i++)
            res[i] = a[i] - b[i];
        return res;
    }
    public double findNorm(double[] arr)
    {
        if (arr==null || arr.length == 0)
            throw new IllegalArgumentException("Illegal vector");
        int len = arr.length;
        double sum=0;
        for(int i=0; i<len; i++)
            sum += Math.pow(arr[i],2);
        sum = Math.sqrt(sum);

        return sum;
    }
    public <T,U>double[][] multiplyMatrices(T a, U b)
    {
        if(a == null || b == null)
            throw new IllegalArgumentException("Illegal matrices");
        double[][]A = createDoubleArr(a);
        double[][]B = createDoubleArr(b);

        if(A[0].length != B.length)
            throw new IllegalArgumentException("Illegal matrices");
        int size = B.length;
        int rows = A.length;
        int cols = B[0].length;
        double[][]res = new double[rows][cols];
        for(int i=0; i<rows ; i++)
            for(int j=0; j<cols; j++)
                for(int k=0; k<size; k++)
                {
                    res[i][j] += A[i][k]*B[k][j];
                }

       return res;
    }
    private <T>double[][] createDoubleArr(T a)
    {
        int dim = 0;
        try{
            Class cl = a.getClass();
            while(cl.isArray())
            {
                dim++;
                cl = cl.getComponentType();
            }
        }catch (Exception e)
        {
            return null;
        }
        if(dim ==0)
            return null;

        double[][] A = null;
        if(dim==1)
        {
            A = new double[((double[]) a).length][1];
            int len = ((double[]) a).length;
            for(int i =0; i<len; i++)
                A[i][0] = ((double[]) a)[i];
        }
        else
        {
            A = new double[((double[][]) a).length][((double[][]) a)[0].length];
            int h = ((double[][]) a).length;
            int len = ((double[][]) a)[0].length;
            for(int i =0; i<h; i++)
                for(int j=0; j<len; j++)
                    A[i][j] = ((double[][]) a)[i][j];
        }
        return A;
    }
    public <T> void printArray(T[] arr)
    {
        if(arr == null || arr.length ==0 )
            throw new IllegalArgumentException("Illegal array");
        boolean isTwoDimArray = false;
            try
            {
                isTwoDimArray = arr[0].getClass().isArray();
            }
            catch (Exception e)
            {
                System.out.println(e);
            }
            int rows = arr.length;
        Formatter formatter = new Formatter();

            for(int i=0;i< rows; i++)
            {

            }
        JTable table = new JTable();



            System.out.println(table);
    }
    public double[][] generateGraph(final int size) {
        if (size <= 0)
            throw new IllegalArgumentException("Size is less than or equal to zero");

        Random random = new Random();

        double[][] matrix = new double[size][size];
        for(int i=0; i<size; i++)
        {
            int sum=0;
            for(int j=0; j<size; j++)
            {
                if(i!=j)
                matrix[i][j] = random.nextInt(2);
                sum += matrix[i][j];
            }
            if(sum ==0) {
                int k = random.nextInt(size);
                while (k == i)
                    k = random.nextInt(size);
                matrix[i][k] = 1;
            }
        }
        int sum=0;
        for (int k = 0; k < size; k++) {
            sum = 0;
            for (int j = 0; j < size; j++)
                sum += matrix[j][k];
            if (sum == 0) {
                int r = random.nextInt(size);
                while (r == k)
                    r = random.nextInt(size);
                matrix[r][k] = 1;
            }
        }
        return matrix;
    }
private double[][] generateRangMatrix(double[][] matrix)
{
    int size = graph.length;
    double[][] res = new double[size][size];

    for(int i=0; i<size; i++)
    {
        for(int j=0; j<size; j++)
        {
            if(i ==j)
                continue;
            int connections =0;
            for(int k=0; k<size; k++)
                connections+= matrix[j][k];
            if(matrix[j][i] == 1 &&  connections !=0)
                res[i][j] = 1.0/connections;
        }
    }

    return res;
}
private void test(double[][] matrix, double[] res)
{
    for(int i=0; i<SIZE; i++)
    {
        double sum=0;
        for (int j = 0; j < SIZE; j++)
            sum+= matrix[i][j]*res[j];
        if(Math.abs(sum - res[i]) < precision)
            System.out.println("true");
        else
            System.out.println("false");
    }

}
    private final int SIZE = 5;
    private double[][] graph;
    private double precision = 0.001;
}


public class Main {
    public static void main(String[] args)
    {
        manager m = new manager();
        double[][] matr = new double[2][2];
        m.powerIteration();
    }
}
