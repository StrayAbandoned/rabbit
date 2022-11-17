package ru.lapshina.producer;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class SimpleSender {
    private final static String QUEUE_NAME = "Hello";
    private final static String EXCANGER_NAME = "Hello_exchanger";

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.exchangeDeclare(EXCANGER_NAME, BuiltinExchangeType.DIRECT);
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            channel.queueBind(QUEUE_NAME, EXCANGER_NAME, "java");
            String msg = "Hello world!";
            channel.basicPublish(EXCANGER_NAME, "java", null, msg.getBytes());
            System.out.println("Sent: " + msg);
        }
    }
}
