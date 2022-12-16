package socket;

import comparator.AlphabetComparator;
import utils.Phone;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.Channels;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class ClientSocket {

        public void startConnecting()
        {
            try
            {
                AsynchronousSocketChannel client = AsynchronousSocketChannel.open();
                Future<Void> result = client.connect(
                        new InetSocketAddress("127.0.0.1", 4444));
                result.get();
                OutputStream os = Channels.newOutputStream(client);
                objos = null;
                InputStream is = Channels.newInputStream(client);
                objis = null;
                sendRequest(os,is);
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        public static void main (String[]args)
        {
            ClientSocket client = new ClientSocket();
            client.startConnecting();
        }

    private void sendRequest(OutputStream os, InputStream is )
    {

        try {
            if(objos == null)
                objos = new ObjectOutputStream(os);
            String request = "New client's request";
            objos.writeObject(request);
            objos.flush();
            if (objis == null)
                objis = new ObjectInputStream(is);
            ArrayList<Phone> response = (ArrayList<Phone>)objis.readObject();
            if(response != null)
                printArrayList(response);

        } catch (Exception e)
        {
            e.printStackTrace();
        }


    }
    private void printArrayList(ArrayList<Phone> phones)
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
    private ObjectOutputStream objos;
        private ObjectInputStream objis;
}
