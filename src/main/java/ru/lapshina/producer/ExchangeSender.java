package ru.lapshina.producer;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class ExchangeSender {
    private final static String EXCANGER_NAME = "EXCHANGER";

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.exchangeDeclare(EXCANGER_NAME, BuiltinExchangeType.DIRECT);
            String msg = "Hello exchange!";
            channel.basicPublish(EXCANGER_NAME, "php", null, msg.getBytes());
            System.out.println("Sent: " + msg);
        }
    }
}
