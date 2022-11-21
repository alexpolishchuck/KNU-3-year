package Lab8.RMI;

import Lab8.socket.ClientSocket;
import Lab8.socket.request.Request;
import Lab8.socket.request.Response;
import com.rabbitmq.client.AMQP;

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
                Request request = new Request(Request.TypeOfRequest.ADD, "WORKS", "FINE");
                client.start_sending(request,server);
                request = new Request(Request.TypeOfRequest.DEL, "WORKS", "FINE");
                client.start_sending(request,server);
                request = new Request(Request.TypeOfRequest.ADD, "WORKS", "FINE");
                client.start_sending(request,server);
                request = new Request(Request.TypeOfRequest.EDIT, new ArrayList<String>(Arrays.asList("WORKS","FINE","WONDERFUL", "NAME")));
                client.start_sending(request,server);
                request = new Request(Request.TypeOfRequest.GET, new ArrayList<String>(Arrays.asList("WORKS","FINE","WONDERFUL", "NAME")));
                client.start_sending(request,server);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        });
        Thread thr2 = new Thread(()->
        {
            try {
                Client client = new Client();
                ServerInterface server = (ServerInterface) Naming.lookup("//127.0.0.1:1099/server");
                Request request = new Request(Request.TypeOfRequest.ADD, "WORKS", "Greatr");
                client.start_sending(request,server);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        });
        thr1.start();
        thr2.start();
    }
    private void start_sending(Request req, ServerInterface server) throws RemoteException {

        Response response = server.AcceptRequest(req);
        showResponse(response);
    }
    private void showResponse(Response response)
    {
        if(response == null || response.getVegies()==null)
            return;
        System.out.println(response.getVegies().toString());
    }
}
