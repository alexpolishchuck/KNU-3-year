package Lab8.RabbitMQ;

import Lab8.socket.request.Request;
import Lab8.socket.request.Response;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public class Client implements AutoCloseable
{
    private Connection connection;
    private Channel channel;
    private String requestQueueName = "rpcqname";

    public Client() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        connection = factory.newConnection();
        channel = connection.createChannel();
    }

    public static void main(String[] argv) {
            try (Client client = new Client()) {
                Request req  = new Request(Request.TypeOfRequest.GET,"","");
                client.start_sending(req);
            } catch (Exception e) {
                e.printStackTrace();
            }

    }

    public void close() throws IOException {
        connection.close();
    }
    private void start_sending(Request req) throws IOException, InterruptedException, ExecutionException {
        final String corrId = UUID.randomUUID().toString();

        String replyQueueName = channel.queueDeclare().getQueue();
        AMQP.BasicProperties props = new AMQP.BasicProperties
                .Builder()
                .correlationId(corrId)
                .replyTo(replyQueueName)
                .build();

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(req);
        channel.basicPublish("", requestQueueName, props, bos.toByteArray());

        final CompletableFuture<Response> response = new CompletableFuture<>();

        String ctag = channel.basicConsume(replyQueueName, true, (consumerTag, delivery) -> {
            if (delivery.getProperties().getCorrelationId().equals(corrId)) {
                ByteArrayInputStream bis = new ByteArrayInputStream(delivery.getBody());
                ObjectInputStream ois = new ObjectInputStream(bis);
                try {
                    response.complete((Response)ois.readObject());

                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }, consumerTag -> {
        });
        showResponse(response.get());
        channel.basicCancel(ctag);

    }

    private void showResponse(Response response)
    {
        if(response == null || response.getVegies()==null)
            return;
        System.out.println(response.getVegies().toString());
    }
}
