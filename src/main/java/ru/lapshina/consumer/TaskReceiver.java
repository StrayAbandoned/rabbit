package ru.lapshina.consumer;

import com.rabbitmq.client.*;

public class TaskReceiver {
    private final static String QUEUE_NAME = "task_queue";
    private final static String EXCANGER_NAME = "task_exchanger";
    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);
        channel.queueBind(QUEUE_NAME, EXCANGER_NAME, "");
        System.out.println("***waiting msg");
        channel.basicQos(3);
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String msg = new String(delivery.getBody(), "UTF-8");
            doWork(msg);
            System.out.println("Received: " + msg);
            channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
        };
        channel.basicConsume(QUEUE_NAME, false, deliverCallback, consumerTag -> {
        });


    }

    private static void doWork(String msg){
        for (char c: msg.toCharArray()){
            if(c == '.'){
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }

    }
}
