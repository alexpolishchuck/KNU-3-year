package Lab8.RabbitMQ;
import Lab7_2.DataBaseManager;
import Lab8.socket.request.Request;
import Lab8.socket.request.Response;
import com.rabbitmq.client.*;

import java.io.*;
import java.sql.SQLException;
import java.util.concurrent.TimeoutException;

public class Server
{
    public Server() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        dbm = new DataBaseManager();
    }
    public static void main(String[]args) throws IOException, TimeoutException, SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        Server server = new Server();
        server.start_accept();
    }

    public void start_accept() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(RPC_QUEUE_NAME, false, false, false, null);
        channel.queuePurge(RPC_QUEUE_NAME);

        channel.basicQos(1);

        System.out.println("Awaiting RPC requests");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            AMQP.BasicProperties replyProps = new AMQP.BasicProperties
                    .Builder()
                    .correlationId(delivery.getProperties().getCorrelationId())
                    .build();

            try {
                ByteArrayInputStream bis = new ByteArrayInputStream(delivery.getBody());
                ObjectInput in = new ObjectInputStream(bis);
                Request req = (Request)in.readObject();
                processRequest(req,channel,delivery,replyProps);
            } catch (Exception e) {
                System.out.println(" [.] " + e);
            }
        };

        channel.basicConsume(RPC_QUEUE_NAME, false, deliverCallback, (consumerTag -> {}));
    }

    private void processRequest(Request req, Channel channel, Delivery delivery,AMQP.BasicProperties replyProps ) throws IOException {
        if(channel == null || req == null || req.getArgs() == null || req.getArgs().size() < 2)
            return;
        Response response = new Response();
        try {
            switch (req.getType()) {
                case ADD -> {
                    dbm.addElement(req.getArgs().get(0), req.getArgs().get(1));
                }
                case DEL -> {
                    dbm.deleteElement(req.getArgs().get(0), req.getArgs().get(1));
                }
                case EDIT -> {
                    if (req.getArgs().size() < 4)
                        return;
                    dbm.editElement(req.getArgs().get(0), req.getArgs().get(1), req.getArgs().get(2),
                            DataBaseManager.parameter.valueOf(req.getArgs().get(3)));
                }
                case GET -> {
                    response = new Response(dbm.showTable());
                }

            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        sendResponse(response,channel,delivery,replyProps);

    }

    private void sendResponse(Response response,
                              Channel channel,
                              Delivery delivery,
                              AMQP.BasicProperties replyProps) throws IOException
    {

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(response);
        channel.basicPublish("", delivery.getProperties().getReplyTo(), replyProps, bos.toByteArray());
        channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
    }
    private static final String RPC_QUEUE_NAME ="rpcqname";
    private DataBaseManager dbm;
}
