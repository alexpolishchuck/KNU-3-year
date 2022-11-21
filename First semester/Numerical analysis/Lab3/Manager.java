package Lab3;

import Lab3.Functions.FunctionInterface;
import Lab3.Functions.System;

import java.rmi.MarshalException;
import java.util.ArrayList;
import java.util.Arrays;

public class Manager {
    public double[] simpleIteration(System system)
    {
        slarManager mngr = new slarManager();
        int size = system.getFunctions().size();
        double []res = new double[size];
        double []prev = new double[size];
        Arrays.fill(res,0.5);
        double[][] A = new double[size][size];
        for(int i=0; i< size; i++)
        {
            for(int j=0; j<size; j++)
            {
                A[i][j] = system.getDerivatives().get(i).get(j).run(res);
            }
        }
     //   if (Math.abs(mngr.findNorm(A)) >=1)
       //     return null;
        do {
            prev = res.clone();
            for(int i=0; i<size; i++)
            {
                res[i] =res[i] - 0.001* system.getFunctions().get(i).run(prev);
            }
        } while(mngr.findNorm(mngr.subtract(res,prev)) >= error);

        showResult("Simple iteration",res,system);
        return res;
    }
public double[] ModifiedNewtonsMethod(System system)
{

    int size = system.getFunctions().size();
    double []res = new double[size];
    Arrays.fill(res,20);
    double []prev = null;

    double[][] A = new double[size][size];
    double[][] F = new double[size][1];
    slarManager SLARmanager = new slarManager();
    for(int i=0; i< size; i++)
    {
        for(int j=0; j<size; j++)
        {
            A[i][j] = system.getDerivatives().get(i).get(j).run(res);
        }
    }
    double[][] Ainv = findInverseMatrix(A);
    do
    {
        for(int i=0; i< size; i++)
        {
            F[i][0] = system.getFunctions().get(i).run(res);
        }
        double[][] multip = SLARmanager.multiplyMatrices(Ainv,F);
        double[] multip1 = new double[size];
        for(int i=0 ; i< size; i++)
            multip1[i] =  multip[i][0];
        prev = res.clone();
        res = SLARmanager.subtract(res,multip1);
       java.lang.System.out.println(SLARmanager.findNorm(SLARmanager.subtract(res,prev)));
    }while(SLARmanager.findNorm(SLARmanager.subtract(res,prev)) >= error);
    showResult("Modified Newton's method",res,system);
    return res;
}
    public double[] NewtonsMethod(System system)
    {

        int size = system.getFunctions().size();
        double []res = new double[size];
        Arrays.fill(res,1);
        double[] z = null;
        double[][] A = new double[size][size];
        double[][] F = new double[size][1];
        slarManager SLARmanager = new slarManager();
        do
        {

            for(int i=0; i< size; i++)
            {
                F[i][0] = system.getFunctions().get(i).run(res);
                for(int j=0; j<size; j++)
                {
                    A[i][j] = system.getDerivatives().get(i).get(j).run(res);
                }
            }
            z = SLARmanager.GaussMethod(A,F);
            res = SLARmanager.subtract(res,z);
        } while(SLARmanager.findNorm(z) >= error);
        showResult("Newton's method",res,system);
        return res;
    }

    public System generateSystem(int n)
    {
        if(n<1)
        return null;
        ArrayList<FunctionInterface> functions = new ArrayList<>();
        ArrayList<ArrayList<FunctionInterface>> derivatives = new ArrayList<>();
        for(int i=0; i< n; i++)
        {
            ArrayList<FunctionInterface> derivrow = new ArrayList<>();
            int curri = i;
            functions.add((arguments)->{
                double res =0;
                for(int k =0; k<n;k++)
                {
                    if(k ==curri)
                    {
                        res+=Math.pow(arguments[k],3);
                        res -= Math.pow(k+1,3);
                    }
                    else
                    {
                        res+= Math.pow(arguments[k],2);
                        res -= Math.pow(k+1,2);
                    }
                }
                return res;});
            for(int j=0 ; j<n; j++)
            {
                int currj = j;
                derivrow.add((arguments)->{
                    if(currj == curri)
                        return Math.pow(arguments[currj],2) * 3;
                    else
                        return arguments[currj] * 2;
                });
            }
            derivatives.add(derivrow);
        }


        return new System(functions,derivatives);
    }

    public static void main (String[] args)
    {
        FunctionInterface func1 = (arguments)->{
            return Math.pow(arguments[0],3)/Math.pow(arguments[1],2) + Math.sin(arguments[1]) - 2;};
        FunctionInterface func2 = (arguments)->{
            return Math.pow(arguments[0],2) + Math.pow(arguments[1],2) - 6;};

        ArrayList<ArrayList<FunctionInterface>> derivatives = new ArrayList<>();
        derivatives.add(new ArrayList<>(Arrays.asList(
                (arguments)->{
                    return 3 * Math.pow(arguments[0],2)/Math.pow(arguments[1],2) ;},
                (arguments)->{
                    return -2 * Math.pow(arguments[0],3)/Math.pow(arguments[1],3) + Math.cos(arguments[1]) ;}

        )));
        derivatives.add(new ArrayList<>(Arrays.asList(
                (arguments)->{
                    return 2*arguments[0];},
                (arguments)->{
                    return  2*arguments[1];}

        )));

        System system = new System(new ArrayList<>(Arrays.asList(func1,func2)),
                derivatives);
        Manager mng = new Manager();
              system = mng.generateSystem(20);
      //  mng.NewtonsMethod(system);
        mng.ModifiedNewtonsMethod(system);
     //   mng.simpleIteration(system);
    }
    public double[][] findInverseMatrix(double A[][])
    {
        slarManager mng = new slarManager();
        int size = A.length;
        double[][] identity = mng.generateIdentityMatrix(size);
        double[][] res = new double[size][size];
        for(int i=0; i<size; i++)
        {
           double[][] column = new double[size][1];
           for(int j=0; j< size; j++)
           {
               column[j][0]= identity[j][i];
           }
            double[] newColumn = mng.GaussMethod(A,column);
            for(int j=0; j< size; j++)
            {
                res[j][i]= newColumn[j];
            }
        }
        return res;
    }
    private void showResult(String method, double[] res, System system)
    {
        java.lang.System.out.println(method+ "\nResult: " + Arrays.toString(res) +"\n");
        double []funcRes = new double[res.length];
        for(int i=0; i< res.length; i++)
        {
            funcRes[i] = system.getFunctions().get(i).run(res);
        }
        java.lang.System.out.println(method+ "\nResults of functions: " + Arrays.toString(funcRes) +"\n");
    }
    private double error = 0.000001;
}
