package RMI;

import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Server  {

    public Server() throws RemoteException
    {

    }
    public static void main (String[] args)
    {
        try
        {

            Registry r = LocateRegistry.createRegistry(1099);
            ServerImpl server = new ServerImpl();
            r.rebind("server",server);

        } catch (Exception e)
        {
            e.printStackTrace();
        }

    }

}
