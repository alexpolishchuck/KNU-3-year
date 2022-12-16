package socket;


import utils.Phone;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.Channels;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class SocketServer
{
public SocketServer()
{
    generateArray();
}
    public void startSocket()
    {
        try
        {
            server = AsynchronousServerSocketChannel.open();
            server.bind(new InetSocketAddress("127.0.0.1",
                    4444));

                startAccept();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void startAccept() throws ExecutionException, InterruptedException, IOException, TimeoutException
    {
        while(server.isOpen())
        {
            Future<AsynchronousSocketChannel> connection = server.accept();
            AsynchronousSocketChannel client = connection.get();
            System.out.println("Starting to accept client");
            if ((client == null) || (!client.isOpen())) {
                client.close();
                continue;
            }
          Thread thr =  new Thread(()->{
              try {
                  ThreadMethod(client);
              } catch (IOException e) {
                  e.printStackTrace();
              }
          });
            thr.start();
        }
    }

        public static void main(String[]args)
        {
            try {
                SocketServer server = new SocketServer();
                server.startSocket();
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        private void ThreadMethod(AsynchronousSocketChannel client) throws IOException {

            System.out.println("Client accepted");
            InputStream connectionInputStream = Channels.newInputStream(client);
            ObjectInputStream  ois = new ObjectInputStream(connectionInputStream);
            OutputStream os = Channels.newOutputStream(client);
            ObjectOutputStream objos = new ObjectOutputStream(os);
            while (client.isOpen())
            {
                try {
                    String request = (String)ois.readObject();
                    System.out.println("Client's request: " + request);
                    writeResponse(client, objos);
                } catch (Exception e)
                {
                    System.out.println("Exception caught in Thread Method");
                    break;
                }
            }
            System.out.println("Client disconnected");
            client.close();
        }
        private void writeResponse(AsynchronousSocketChannel client, ObjectOutputStream objos) throws IOException, SQLException {
                objos.writeObject(phones);
                objos.flush();

        }
        private void generateArray()
        {
            phones = new ArrayList<>();
            for(int i=0; i<4; i++)
                phones.add(new Phone());
        }
        private ArrayList<Phone> phones;
        private AsynchronousServerSocketChannel server;
}


