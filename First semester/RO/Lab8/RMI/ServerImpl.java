package Lab8.RMI;

import Lab7_2.DataBaseManager;
import Lab8.socket.request.Request;
import Lab8.socket.request.Response;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;

public class ServerImpl  extends UnicastRemoteObject implements ServerInterface{
    public ServerImpl() throws RemoteException, SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        super();
        dbm = new DataBaseManager();
    }
    @Override
    public Response AcceptRequest(Request request) throws RemoteException {
        System.out.println("Request accepted");
        Response response = new Response();
        if( request == null || request.getArgs() == null || request.getArgs().size() < 2)
            return response;

        try {
            switch (request.getType()) {
                case ADD -> {
                    dbm.addElement(request.getArgs().get(0), request.getArgs().get(1));
                    break;
                }
                case DEL -> {
                    dbm.deleteElement(request.getArgs().get(0), request.getArgs().get(1));
                    break;
                }
                case EDIT -> {
                    if (request.getArgs().size() < 4)
                        break;
                    dbm.editElement(request.getArgs().get(0), request.getArgs().get(1), request.getArgs().get(2),
                            DataBaseManager.parameter.valueOf(request.getArgs().get(3)));
                    break;
                }
                case GET -> {
                    response = new Response(dbm.showTable());
                    break;
                }

            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }

       return response;
    }
    private DataBaseManager dbm;
}
