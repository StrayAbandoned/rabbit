package ru.lapshina.producer;

import com.rabbitmq.client.*;

import java.util.Scanner;

public class HomeWorkProducer {
    private final static String EXCANGER_NAME = "Homework";

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel();
             Scanner sc = new Scanner(System.in)) {
            String msg = null;
            channel.exchangeDeclare(EXCANGER_NAME, BuiltinExchangeType.FANOUT);
            while (true){
                String m = sc.nextLine();
                int i = m.indexOf(' ');
                msg = m.substring(i+1);
                channel.queueDeclare(m.substring(0, i), true, false, false, null);
                channel.basicPublish(EXCANGER_NAME, msg, MessageProperties.PERSISTENT_TEXT_PLAIN, msg.getBytes());
                System.out.println("Sent: " + msg);
            }



        }
    }
}
