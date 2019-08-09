package com.liyghting.rabbitmqdemo;


import com.rabbitmq.client.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RabbitmqReceiveTest {
    private static String queue_name = "test";
    private static Logger logger = LoggerFactory.getLogger(RabbitmqReceiveTest.class);
    public static void main(String[] args) {
        Channel channel = null;
        try {
            channel = RabbitmqChannelUtils.getChannel();
            AMQP.Queue.DeclareOk declareOk = channel.queueDeclare(queue_name, false, false, false, null);
            QueueingConsumer queueingConsumer = new QueueingConsumer(channel);
            channel.basicConsume(queue_name, queueingConsumer);
            while (true) {
                QueueingConsumer.Delivery delivery = queueingConsumer.nextDelivery();
                String msg = new String(delivery.getBody());
                logger.info(msg);
                channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            RabbitmqChannelUtils.close(channel);
        }
    }
}
