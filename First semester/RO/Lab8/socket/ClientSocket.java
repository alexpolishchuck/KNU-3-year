package Lab8.socket;

import Lab8.socket.request.Request;
import Lab8.socket.request.vegetable;

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
        //        sendRequest(client,Request.TypeOfRequest.ADD,new ArrayList<String>(Arrays.asList("YAY","YAY")),os,is);
       //         sendRequest(client,Request.TypeOfRequest.ADD,new ArrayList<String>(Arrays.asList("DEL","DEL")),os ,is);
          //      sendRequest(client,Request.TypeOfRequest.DEL,new ArrayList<String>(Arrays.asList("DEL","DEL")),os ,is);
           //     sendRequest(client,Request.TypeOfRequest.EDIT,new ArrayList<String>(Arrays.asList("YAY","YAY","WOW","NAME"))
           //             ,os, is);
                sendRequest(client,Request.TypeOfRequest.GET,null,os, is);
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

    private void sendRequest(AsynchronousSocketChannel client, Request.TypeOfRequest type, ArrayList<String> args,
                             OutputStream os, InputStream is )
    {
        Request request = new Request(type,args);

        try {
            if(objos == null)
                objos = new ObjectOutputStream(os);
            objos.writeObject(request);
            objos.flush();
            if (objis == null)
                objis = new ObjectInputStream(is);
            ArrayList<vegetable> response = (ArrayList<vegetable>)objis.readObject();
            if(response != null)
                printArrayList(response);

        } catch (Exception e)
        {
            e.printStackTrace();
        }


    }
    private void printArrayList(ArrayList<vegetable> vegs)
    {
        System.out.println(vegs.toString());
    }
    private ObjectOutputStream objos;
        private ObjectInputStream objis;
}
