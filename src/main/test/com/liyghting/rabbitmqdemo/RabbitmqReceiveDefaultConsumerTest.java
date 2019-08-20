package com.liyghting.rabbitmqdemo;


import com.rabbitmq.client.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Scanner;

public class RabbitmqReceiveDefaultConsumerTest {
    private static String queue_name = "test";
    private static Logger logger = LoggerFactory.getLogger(RabbitmqReceiveDefaultConsumerTest.class);
    public static void main(String[] args) {
        Channel channel = null;
        try {
            channel = RabbitmqChannelUtils.getChannel();
            AMQP.Queue.DeclareOk declareOk = channel.queueDeclare(queue_name, false, false, false, null);
            channel.basicConsume(queue_name, true, new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    String msg = new String(body);
                    logger.info(msg);

                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            RabbitmqChannelUtils.close(channel);
        }
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
