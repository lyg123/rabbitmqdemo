package com.liyghting.rabbitmqdemo;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RabbitmqChannelUtils {


    public static Channel getChannel() {
        try {
            return getConnection().createChannel();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static void close(Channel channel) {
        try {
            channel.close();
            channel.getConnection().close();
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
    }

    //----------------------------

    private static Connection getConnection() {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setPort(5672);
        factory.setUsername("admin");
        factory.setPassword("admin");
        try {
            return factory.newConnection();
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
            return null;
        }
    }
}
