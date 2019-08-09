package com.liyghting.rabbitmqdemo;


import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;

import java.io.IOException;

public class RabbitmqConfirmSendTest {
    private static String queue_name = "test";

    public static void main(String[] args) {
        Channel channel = null;
        try {
            channel = RabbitmqChannelUtils.getChannel();
            AMQP.Queue.DeclareOk declareOk = channel.queueDeclare(queue_name, false, false, false, null);
            String message = "Hello world Rabbitmq...";
            channel.confirmSelect();
            channel.basicPublish("", queue_name, null, message.getBytes());
            if(!channel.waitForConfirms()) {
                // do something
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();

        } finally {
            RabbitmqChannelUtils.close(channel);
        }
    }
}
