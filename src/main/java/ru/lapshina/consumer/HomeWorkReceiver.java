package ru.lapshina.consumer;

import com.rabbitmq.client.*;

import java.util.Scanner;

public class HomeWorkReceiver {
    private final static String EXCANGER_NAME = "Homework";

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        Scanner s = new Scanner(System.in);
        channel.exchangeDeclare(EXCANGER_NAME, BuiltinExchangeType.FANOUT);

        String queueName = s.nextLine();
        channel.queueBind(queueName, EXCANGER_NAME, queueName);
        System.out.println("***waiting msg");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String msg = new String(delivery.getBody(), "UTF-8");
            System.out.println("Received: " + msg);
        };
        channel.basicConsume(queueName, false, deliverCallback, consumerTag -> {
        });


    }
}
