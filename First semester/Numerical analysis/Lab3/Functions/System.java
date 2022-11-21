package Lab3.Functions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.PropertyResourceBundle;

public class System {


    public System()
    {
        functions = new ArrayList<>();
        derivatives = new ArrayList<>();
    }
    public System(ArrayList<FunctionInterface> functions, ArrayList<ArrayList<FunctionInterface>> derivatives)
    {
        this.functions = functions;
        this.derivatives =derivatives;

    }

    public ArrayList<ArrayList<FunctionInterface>> getDerivatives() {
        return derivatives;
    }
    public ArrayList<FunctionInterface> getFunctions() {
        return functions;
    }
    public double calculate(FunctionInterface func, double[] args)
    {
        return func.run(args);
    }
    private ArrayList<FunctionInterface> functions;
    private ArrayList<ArrayList<FunctionInterface>> derivatives;

}
