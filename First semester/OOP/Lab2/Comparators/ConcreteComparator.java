package Comparators;

import FileManager.BankDeposit;

import java.util.Comparator;

public class ConcreteComparator implements Comparator {
    @Override
    public int compare(Object one, Object two)
    {
        BankDeposit one_ = (BankDeposit)one;
        BankDeposit two_ = (BankDeposit)two;
        if(Integer.parseInt(one_.getParameter(BankDeposit.FIELD.ID)) > Integer.parseInt(two_.getParameter(BankDeposit.FIELD.ID)))
             return 1;
        if(Integer.parseInt(one_.getParameter(BankDeposit.FIELD.ID)) < Integer.parseInt(two_.getParameter(BankDeposit.FIELD.ID)))
            return -1;
        return 0;
    }
}
