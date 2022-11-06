package FileManager;

import java.util.ArrayList;
import java.util.Arrays;

public class BankDeposit {

    public BankDeposit()
    {
        parameters = new ArrayList<>(8);
        for(int i=0; i<8 ;i++)
            parameters.add("");
    }
   public BankDeposit(String name, String country, String type,String depositor,
                      String id,String amount,String profitability,String timeConstraints)
   {
       parameters = new ArrayList<>(8);
       parameters.add(name);
       parameters.add(country);
       parameters.add(type);
       parameters.add(depositor);
       parameters.add(id);
       parameters.add(amount);
       parameters.add(profitability);
       parameters.add(timeConstraints);
   }
   public void setParameter(FIELD field, String string )
   {
       parameters.set(field.getTypeId(),string);
   }

   public String getParameter(FIELD field)
   {
       return parameters.get(field.getTypeId());
   }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[Deposit] |");
        stringBuilder.append(" Name of Bank: " + parameters.get(FIELD.NAME.getTypeId()) + "|");
        stringBuilder.append(" Country: " + parameters.get(FIELD.COUNTRY.getTypeId())+ "|");
        stringBuilder.append(" Type: " + parameters.get(FIELD.TYPE.getTypeId())+ "|");
        stringBuilder.append(" Depositor: " + parameters.get(FIELD.DEPOSITOR.getTypeId())+ "|");
        stringBuilder.append(" Depositor's id: " + parameters.get(FIELD.ID.getTypeId())+ "|");
        stringBuilder.append(" Amount: " + parameters.get(FIELD.AMOUNT.getTypeId())+ "|");
        stringBuilder.append(" Profitability: " + parameters.get(FIELD.PROF.getTypeId())+ "|");
        stringBuilder.append(" Time constraints: " + parameters.get(FIELD.TIME.getTypeId())+ "|");
        return stringBuilder.toString();
    }

    public enum FIELD
{NAME(0), COUNTRY(1), TYPE(2), DEPOSITOR(3), ID(4), AMOUNT(5), PROF(6), TIME(7), ERROR(8);

    public int getTypeId()
    {
        return typeId;
    }
    private FIELD(int id)
    {
        typeId = id;
    }
    static public FIELD fromInt(int i) {
        switch (i)
        {
            case(0):
                return NAME;
            case(1):
                return COUNTRY;
            case(2):
                return TYPE;
            case(3):
                return DEPOSITOR;
            case(4):
                return ID;
            case(5):
                return AMOUNT;
            case(6):
                return PROF;
            case(7):
                return TIME;
        }
        return ERROR;
    }
    private int typeId;
}
    private ArrayList<String> parameters;

}
