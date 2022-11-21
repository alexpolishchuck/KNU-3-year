package Lab8.socket;

import Lab7_2.DataBaseManager;
import Lab8.socket.request.Request;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.Channels;
import java.sql.SQLException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class SocketServer
{

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
                dm = new DataBaseManager();

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
                    Request req ;
                    req = (Request)ois.readObject();
                    System.out.println("RECEIVED "+ req.getType().getValue());
                    processRequest(req);
                    writeResponse(req.getType(),client, objos);
                } catch (Exception e)
                {
                    System.out.println("Exception caught in Thread Method");
                    break;
                }
            }
            System.out.println("Client disconnected");
            client.close();
        }
        private void writeResponse(Request.TypeOfRequest type, AsynchronousSocketChannel client, ObjectOutputStream objos) throws IOException, SQLException {


            if(type == Request.TypeOfRequest.GET)
            {
                objos.writeObject(dm.showTable());
                objos.flush();
            }
            else
                objos.writeObject(null);

        }

        private void processRequest(Request req) {
            if (req == null || req.getArgs() == null || req.getArgs().size() < 2)
                return;
            try {
                switch (req.getType()) {
                    case ADD -> {
                        dm.addElement(req.getArgs().get(0), req.getArgs().get(1));
                    }
                    case DEL -> {
                        dm.deleteElement(req.getArgs().get(0), req.getArgs().get(1));
                    }
                    case EDIT -> {
                        if (req.getArgs().size() < 4)
                            return;
                        dm.editElement(req.getArgs().get(0), req.getArgs().get(1), req.getArgs().get(2),
                                DataBaseManager.parameter.valueOf(req.getArgs().get(3)));
                    }

                }
            } catch (SQLException e)
            {
                e.printStackTrace();
            }
        }

        private static DataBaseManager dm;
        private AsynchronousServerSocketChannel server;
}


