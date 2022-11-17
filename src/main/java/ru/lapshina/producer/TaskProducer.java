package ru.lapshina.producer;

import com.rabbitmq.client.*;

public class TaskProducer {
    private final static String EXCANGER_NAME = "task_exchanger";

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.exchangeDeclare(EXCANGER_NAME, BuiltinExchangeType.FANOUT);
            String msg1 = "Task...";
            for (int i=0; i<10; i++){
                channel.basicPublish(EXCANGER_NAME, "", MessageProperties.PERSISTENT_TEXT_PLAIN, msg1.getBytes());
                System.out.println("Sent: " + msg1);
            }

        }
    }
}
