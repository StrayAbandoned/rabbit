package ru.lapshina.producer;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class DoubleDirectSender {
    private final static String EXCANGER_NAME = "Double";

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.exchangeDeclare(EXCANGER_NAME, BuiltinExchangeType.DIRECT);
            String msg1 = "php!";
            String msg2 = "java!";
            channel.basicPublish(EXCANGER_NAME, msg1, null, msg1.getBytes());
            channel.basicPublish(EXCANGER_NAME, msg2, null, msg2.getBytes());
            System.out.println("Sent: " + msg1);
            System.out.println("Sent: " + msg2);
        }
    }
}
