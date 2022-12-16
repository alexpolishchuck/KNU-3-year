package RMI;

import utils.Phone;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface ServerInterface extends Remote {
    ArrayList<Phone> AcceptRequest(String req) throws RemoteException;
}
