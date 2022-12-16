package RMI;

import utils.Phone;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.ArrayList;

public class ServerImpl  extends UnicastRemoteObject implements RMI.ServerInterface {
    public ServerImpl() throws RemoteException, SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        super();
        generateArray();
    }
    @Override
    public ArrayList<Phone> AcceptRequest(String req) throws RemoteException {
        System.out.println("Request accepted");
       return phones;
    }
    private void generateArray()
    {
        phones = new ArrayList<>();
        for(int i=0; i<4; i++)
            phones.add(new Phone());
    }
    private ArrayList<Phone> phones;
}
