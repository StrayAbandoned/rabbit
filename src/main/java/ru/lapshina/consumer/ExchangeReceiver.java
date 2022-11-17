package ru.lapshina.consumer;

import com.rabbitmq.client.*;

public class ExchangeReceiver {

    private final static String EXCANGER_NAME = "EXCHANGER";
    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(EXCANGER_NAME, BuiltinExchangeType.DIRECT);
        String queueName = channel.queueDeclare().getQueue();
        System.out.println(queueName);
        channel.queueBind(queueName, EXCANGER_NAME, "php");
        System.out.println("***waiting msg");
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String msg = new String(delivery.getBody(), "UTF-8");
            System.out.println("Received: " + msg);
        };
        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {
        });


    }
}
