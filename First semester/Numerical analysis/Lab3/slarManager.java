package Lab3;

import java.util.Arrays;
import java.util.Random;

public class slarManager {
    public slarManager(){

        //matrixA = generateMatrix(SIZE,SIZE);
        matrixA = generateHilbertMatrix(SIZE);
        matrixB = generateMatrix(SIZE,1);
    }


    public double[] GaussMethod(double[][] A, double[][]B){
            final int size = A.length;
            double[][] matrixL = generateIdentityMatrix(size);
            double[][] copyA = A;
             double[][] copyB = B;
            for(int i= 0; i<size;i++){
                double max = copyA[i][i];
                int pos = i;
                double[][]P = generateIdentityMatrix(size);
                double[][]M = generateIdentityMatrix(size);
                for(int j =i; j<size; j++)
                    if(max < copyA[j][i])
                    {
                        max = copyA[j][i];
                        pos = j;
                    }
                if(max ==0)
                    continue;
                P[i][i]=0;
                P[i][pos]=1;
                P[pos][pos]=0;
                P[pos][i]=1;
                matrixL = multiplyMatrices(P,matrixL);
                copyA = multiplyMatrices(P,copyA);

                for(int j=i;j<size;j++)
                {
                    if(j!=i)
                        M[j][i]=-(copyA[j][i]/max);
                    else
                        M[j][i]=1/copyA[j][i];

                }
                matrixL = multiplyMatrices(M,matrixL);
                copyA = multiplyMatrices(M,copyA);


            }
            copyB = multiplyMatrices(matrixL,copyB);
            double[] res = new double[size];
            for(int i=size-1; i>=0;i--)
            {
                double subtractor = 0;
                for(int j= size-1; j!=i; j--)
                {
                    subtractor += copyA[i][j]*res[j];
                }
                copyB[i][0]-=subtractor;
                if(copyA[i][i]!=0)
                res[i] = copyB[i][0]/copyA[i][i];
            }

    //    testResults(res);
        return res;
    }

    public void JacobiMethod(){
        matrixA = generateDiagDomMatrix(SIZE);
      //  matrixA = generateHilbertMatrix(SIZE);
        double[]approx = new double[SIZE];
        double[]prev =new double[SIZE];

        do {
            prev = approx.clone();
            for(int i=0; i<SIZE;i++)
            {
                approx[i] = matrixB[i][0]/matrixA[i][i];
                for(int j=0; j<SIZE;j++)
                {
                    if(i!=j)
                    approx[i]-=matrixA[i][j]/matrixA[i][i] * prev[j];
                }
            }
        }while(findNorm(subtract(approx,prev))> precision);
        System.out.println("JacobiMetdod\nA:\n");
        printArray(matrixA);
        System.out.println("B:\n");
        printArray(matrixB);
        System.out.println("Result:\n" + Arrays.toString(approx));
        testResults(approx);

    }
    public void SeidelMethod(){
        matrixA = generateDiagDomMatrix(SIZE);
      //  matrixA = generateHilbertMatrix(SIZE);
        double[]approx = new double[SIZE];
        double[]prev =new double[SIZE];

        do {
            prev = approx.clone();
            for(int i=0; i<SIZE;i++)
            {
                approx[i] = matrixB[i][0]/matrixA[i][i];
                for(int j=0; j<SIZE;j++)
                {
                    if(i>j)
                        approx[i]-=matrixA[i][j]/matrixA[i][i] * approx[j];
                    else if(i<j)
                        approx[i]-=matrixA[i][j]/matrixA[i][i] * prev[j];
                }
            }
        }while(findNorm(subtract(approx,prev))> precision);
        System.out.println("SeidelMetdod\nA:\n");
        printArray(matrixA);
        System.out.println("B:\n");
        printArray(matrixB);
        System.out.println("Result:\n" + Arrays.toString(approx));
        testResults(approx);

    }
    public double findNorm(double[] vec){
        double res=0;
        for(int i=0; i<vec.length;i++)
            res+=vec[i]*vec[i];
        return Math.sqrt(res);
    }
    public double findNorm(double[][] A)
    {
        final int size = A.length;
        double[][] matrixL = generateIdentityMatrix(size);
        double[][] copyA = A;
        double norm = 1;
        for(int i= 0; i<size;i++){

            double max = copyA[i][i];
            int pos = i;
            double[][]P = generateIdentityMatrix(size);
            double[][]M = generateIdentityMatrix(size);
            for(int j =i; j<size; j++)
                if(max < copyA[j][i])
                {
                    max = copyA[j][i];
                    pos = j;
                }
            if(max ==0)
                continue;
            P[i][i]=0;
            P[i][pos]=1;
            P[pos][pos]=0;
            P[pos][i]=1;
            if(pos!=i)
            norm *=-1;
            matrixL = multiplyMatrices(P,matrixL);
            copyA = multiplyMatrices(P,copyA);

            for(int j=i;j<size;j++)
            {
                if(j!=i)
                    M[j][i]=-(copyA[j][i]/max);
                else {
                    M[j][i] = 1 / copyA[j][i];
                    norm *= copyA[j][i];
                }

            }
            matrixL = multiplyMatrices(M,matrixL);
            copyA = multiplyMatrices(M,copyA);


        }
        return norm;
    }
    double[]add(double[] a, double[] b)
    {
        if(a.length != b.length)
            return null;
        int len = a.length;
        double[]res= new double[len];
        for(int i=0; i<len;i++)
            res[i] = a[i] + b[i];

        return res;
    }
    double[]subtract(double[] a, double[] b)
    {
        if(a.length != b.length)
            return null;
        int len = a.length;
        double[]res= new double[len];
        for(int i=0; i<len;i++)
            res[i] = a[i] - b[i];

        return res;
    }
    private void printArray(double[][]arr){
        int len = arr.length;
        for(int i=0; i<len; i++)
            System.out.println(Arrays.toString(arr[i]));
    }

    private void testResults(double[]res){
        int len = res.length;

        for(int i=0; i<SIZE; i++)
        {
            double sum =0;
            for(int j=0; j<SIZE; j++)
            {
                sum +=matrixA[i][j] * res[j];

            }
            if(Math.abs(sum - matrixB[i][0])<testPrecision)
                System.out.println("TRUE");
            else System.out.println("FALSE");

        }
    }
    private double[][] generateCopy(double[][] matrix){
        int len = matrix.length;
        double[][] res = new double[len][matrix[0].length];

        for(int i=0; i<len;i++)
            res[i] = matrix[i].clone();
        return res;
    }
    public double[][] multiplyMatrices(double[][] A, double[][]B)
    {
        int heightA = A.length;
        int lenB = B[0].length;
        int lenA = A[0].length;

        double[][] res = new double[heightA][lenB];
        for(int i=0; i<heightA; i++)
        {
            for(int j=0; j<lenB; j++)
            {
                res[i][j]=0;
                for(int k=0; k<lenA; k++)
                {
                    res[i][j]+= A[i][k] * B[k][j];
                }
            }

        }
        return res;
    }

    private double[][] generateMatrix(int len, int height){
        double[][] matrix = new double[len][height];
        Random rand = new Random();
        for(int i=0; i<len; i++)
        {
            for(int j=0; j<height; j++)
                matrix[i][j] = rand.nextInt(max_number);
        }
        return matrix;
    }
    private double[][] generateDiagDomMatrix(int size)
    {
        double[][] matrix = new double[size][size];
        Random rand = new Random();
        for(int i=0; i<size; i++)
        {
            int sum=0;
            for(int j=0; j<size;j++)
                if (i != j) {
                    matrix[i][j] = rand.nextInt(max_number);
                    sum += matrix[i][j];
                }
                matrix[i][i] = sum + rand.nextInt(max_number);

        }
        return matrix;
    }
    public double[][]  generateIdentityMatrix(int size){
        double[][] matrix = new double[size][size];
        for(int i=0; i<size; i++)
        {
            matrix[i][i]=1;
        }
        return matrix;
    }

    private double[][] generateHilbertMatrix(int size){

        double[][] matrix = new double[size][size];
        for(int i=0; i<size; i++){
            int counter = i+1;
            for(int k=0; k<size; k++)
            {
                matrix[k][i] = 1.0/counter;
                counter++;
            }



        }
            return matrix;
    }

    private final int SIZE = 8;
    private int max_number = 10;
    private double matrixA[][];
    private double matrixB[][];

    private final double testPrecision = 0.1;
    private final double precision = 0.0001;
}



