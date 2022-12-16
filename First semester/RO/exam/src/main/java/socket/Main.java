package socket;

public class Main {

    public static void main(String[]args)
    {
        SocketServer server = new SocketServer();
        ClientSocket client = new ClientSocket();
        Thread serv = new Thread(()->{server.startSocket();});
        Thread cl = new Thread(()->{client.startConnecting();});
    }

}
