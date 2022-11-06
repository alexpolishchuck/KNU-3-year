import Comparators.ConcreteComparator;
import FileManager.FileManager;
import FileManager.BankDeposit;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args)
    {
        FileManager fm = new FileManager();
        ArrayList<BankDeposit> deposits = null;
       // fm.parseFileDOM();
    //    fm.parseFileSAX();
        deposits =  fm.parseFileStAX();
        deposits.sort(new ConcreteComparator());
        for(BankDeposit i : deposits)
            System.out.println(i.toString());

    }
}