package FileManager.SAXHandler;

import FileManager.BankDeposit;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SAXHandler extends DefaultHandler {
    public SAXHandler()
    {
        Boolean[] arr = new Boolean[8];
        Arrays.fill(arr,false);
        fields = Arrays.asList(arr);
        deposits = new ArrayList<>();
    }
    @Override
    public void startElement(
            String uri,
            String localName,
            String qName,
            Attributes attributes) {

        if (qName.equalsIgnoreCase("deposit"))
        {
            String id = attributes.getValue("id");
            System.out.printf("Staff id : %s%n", id);
            deposits.add(new BankDeposit());
            deposits.get(deposits.size()-1).setParameter(BankDeposit.FIELD.NAME,attributes.getValue("name"));
        }
        else if(qName.equalsIgnoreCase("Country"))
        {
            fields.set(BankDeposit.FIELD.COUNTRY.getTypeId(),true);
        }
        else if(qName.equalsIgnoreCase("Type"))
        {
            fields.set(BankDeposit.FIELD.TYPE.getTypeId(),true);
        }
        else if(qName.equalsIgnoreCase("Depositor"))
        {
            deposits.get(deposits.size()-1).setParameter(BankDeposit.FIELD.ID,attributes.getValue("account_id"));
            fields.set(BankDeposit.FIELD.DEPOSITOR.getTypeId(),true);
        }
        else if(qName.equalsIgnoreCase("Amount"))
        {
            fields.set(BankDeposit.FIELD.AMOUNT.getTypeId(),true);
        }
        else if(qName.equalsIgnoreCase("Profitability"))
        {
            fields.set(BankDeposit.FIELD.PROF.getTypeId(),true);
        }
        else if(qName.equalsIgnoreCase("Time_constraints"))
        {
            fields.set(BankDeposit.FIELD.TIME.getTypeId(),true);
        }
        data = new StringBuilder();

    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException
    {
        data.append(new String(ch,start,length));
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException
    {
     for(int i=0; i<fields.size(); i++)
     {
         if(fields.get(i) == true)
         {
             getValue(BankDeposit.FIELD.fromInt(i));
             break;
         }
     }


    }
    public ArrayList<BankDeposit> getDeposits()
    {
        return deposits;
    }
    private void getValue(BankDeposit.FIELD field)
    {
        fields.set(field.getTypeId(),false);
        deposits.get(deposits.size()-1).setParameter(field, data.toString());
    }
    private ArrayList<BankDeposit> deposits;
    private StringBuilder data;
    List<Boolean> fields;
}
