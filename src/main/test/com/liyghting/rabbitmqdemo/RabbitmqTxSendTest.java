package com.liyghting.rabbitmqdemo;


import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;

import java.io.IOException;

public class RabbitmqTxSendTest {
    private static String queue_name = "test";

    public static void main(String[] args) {
        Channel channel = null;
        try {
            channel = RabbitmqChannelUtils.getChannel();
            AMQP.Queue.DeclareOk declareOk = channel.queueDeclare(queue_name, false, false, false, null);
            String message = "Hello world Rabbitmq...";
            channel.txSelect();
            channel.basicPublish("", queue_name, null, message.getBytes());
            channel.txCommit();
        } catch (IOException e) {
            e.printStackTrace();
            try {
                channel.txRollback();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } finally {
            RabbitmqChannelUtils.close(channel);
        }
    }
}
