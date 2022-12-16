package RMI;


import comparator.AlphabetComparator;
import utils.Phone;

import java.io.*;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class Client {
    public static void main(String[] args) throws MalformedURLException, NotBoundException, RemoteException {

        Thread thr1 = new Thread(()->
        {
            try {
                Client client = new Client();
                ServerInterface server = (ServerInterface) Naming.lookup("//127.0.0.1:1099/server");

                client.start_sending("Request from client",server);

            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        });

        thr1.start();
      //  thr2.start();
    }
    private void start_sending(String req, ServerInterface server) throws RemoteException {

        ArrayList<Phone> response = server.AcceptRequest(req);
        showResponse(response);
    }
    private void showResponse(ArrayList<Phone> phones)
    {
        System.out.println("More than max time");
        for(Phone i : phones)
        {
            if(i.getTime() > Phone.maxTime)
                System.out.println(i);
        }
        System.out.println("----------------------");
        System.out.println("Used longdistance");
        for(Phone i : phones)
        {
            if(i.HasUsedLongDistance())
                System.out.println(i);
        }
        System.out.println("----------------------");
        phones.sort(new AlphabetComparator());
        System.out.println("Sorted by surname");
        for(Phone i : phones)
        {
            System.out.println(i);
        }
    }
}
