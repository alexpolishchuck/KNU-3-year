package Lab8.RMI;

import Lab8.socket.request.Request;
import Lab8.socket.request.Response;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerInterface extends Remote {
    Response AcceptRequest(Request request) throws RemoteException;;
}
