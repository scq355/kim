package com.kim.rabbitmq.connect;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.TimeoutException;

public class Connect01 {
    public static void main(String[] args) throws IOException {

        try {
            Connection connect1 = ConnectMethod1.getConnect();
            final Channel channel = connect1.createChannel();
            channel.basicQos(1);
            // 持久化的、非自动删除的、绑定类型为direct的交换器
            channel.exchangeDeclare("exchangeName", "direct", true);
            // 非持久化的、排他的、自动删除的队列（此队列的名称由RabbitMQ自动生成）
            // 声明的队列具备如下特性：只对当前应用中同一个Connection层面可用
            // 同一个Connection的不同Channel可共用，并且也会在应用连接断开时自动删除
            String queueName = channel.queueDeclare().getQueue();

            // 持久化的、非排他的、非自动删除的，确定的已知名称
            channel.queueDeclare("queueName", true, false, false, null);

            // 使用路由键将队列和交换器绑定起来
            channel.queueBind(queueName, "exchangeName", "routingKey");

            byte[] message = "This is message content".getBytes();

            channel.basicPublish("exchangeName", "routingKey", null, message);
            channel.basicPublish("exchangeName", "routingKey", true, MessageProperties.PERSISTENT_BASIC, message);
            channel.basicPublish("exchangeName", "routingKey", new AMQP.BasicProperties.Builder()
                    .contentType(MessageProperties.TEXT_PLAIN.getContentType())
                    .deliveryMode(2)
                    .priority(1)
                    .userId("hidden")
                    .build(), message);

            Map<String, Object> headers = new HashMap<String, Object>();
            headers.put("location", "here");
            channel.basicPublish("exchangeName", "routingKey", new AMQP.BasicProperties.Builder()
                    .contentType(MessageProperties.TEXT_PLAIN.getContentType())
                    .expiration("60000")
                    .headers(headers)
                    .deliveryMode(2)
                    .priority(1)
                    .userId("hidden")
                    .build(), message);


            boolean autoAck = false;
            channel.basicQos(64);
            channel.basicConsume("queueName", autoAck, "consumerTag", new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    String routingKey = envelope.getRoutingKey();
                    String contentType = properties.getContentType();
                    long deliveryTag = envelope.getDeliveryTag();
                    channel.basicAck(deliveryTag, false);
                }
            });


            GetResponse response = channel.basicGet("queueName", false);
            channel.basicAck(response.getEnvelope().getDeliveryTag(), false);

            connect1.addShutdownListener(new ShutdownListener() {
                public void shutdownCompleted(ShutdownSignalException cause) {
                    if (cause.isHardError()) {
                        Object reference = cause.getReference();
                        if (!cause.isInitiatedByApplication()) {
                            Method reason = cause.getReason();
                        }
                    } else {
                        Object reference = cause.getReference();
                    }
                }
            });


            channel.basicPublish("exchangeName", "", true,
                    MessageProperties.PERSISTENT_TEXT_PLAIN, "mandatory test".getBytes());
            channel.addReturnListener(new ReturnListener() {
                public void handleReturn(int replyCode, String replyText, String exchange, String routingKey,
                                         AMQP.BasicProperties properties, byte[] body) throws IOException {
                    String message = new String(body);
                    System.out.println("Basic.Return message = " + message);
                }
            });


            HashMap<String, Object> properties = new HashMap<String, Object>();
            properties.put("alternate-exchange", "AE");
            properties.put("x-expires", 180000);

            channel.exchangeDeclare("normalExchange", "direct", true, false, properties);
            channel.exchangeDeclare("AE", "fanout", true, false, null);
            HashMap<String, Object> queueParams = new HashMap<String, Object>();
            queueParams.put("x-message-ttl", 6000);
            queueParams.put("x-dead-letter-exchange", "dlx_exchange");
            queueParams.put("x-dead-letter--routing-key", "dlx-routing-key");
            channel.queueDeclare("normalQueue", true, false, false, queueParams);
            channel.queueBind("normalQueue", "normalExchange", "normalKey");
            channel.queueDeclare("unRoutedQueue", true, false, false, null);
            channel.queueBind("unRoutedQueue", "AE", "");

            AMQP.BasicProperties.Builder builder = new AMQP.BasicProperties.Builder();
            builder.deliveryMode(2);
            builder.expiration("6000");
            AMQP.BasicProperties basicProperties = builder.build();
            channel.basicPublish("normalExchange", "routingKey", true, basicProperties, "Test".getBytes());

            channel.exchangeDeclare("exchange.dlx", "direct", true);
            channel.exchangeDeclare("exchange.normal ", "fanout ", true);
            Map<String, Object> argss = new HashMap<String, Object>();
            argss.put("x-message-ttl", 10000);
            argss.put("x-dead-letter-exchange", "exchange.dlx");
            argss.put("x-dead-letter-routing-key", "routingKey");
            argss.put("queue.priority", 10);
            channel.queueDeclare("queue.normal", true, false, false, argss);
            channel.queueBind("queue.normal", "exchange.normal", "");
            channel.queueDeclare("queue.dlx", true, false, false, null);
            channel.queueBind("queue.dlx", "exchange.dlx", "routingKey");
            channel.basicPublish("exchange.normal", " rk ",
                    MessageProperties.PERSISTENT_TEXT_PLAIN, "dlx ".getBytes());

            channel.exchangeDeclare("exchange.dlx", "direct", true);
            channel.queueDeclare("queue.dlx", true, false, false, null);
            channel.queueBind("queue.dlx", "exchange.dlx", "dlx-routing-key");
            channel.basicPublish("exchange.normal", "dlx-routing-key", MessageProperties.PERSISTENT_TEXT_PLAIN, "dlx".getBytes());

            channel.exchangeDeclare("exchange.normal", "fanout", true);
            HashMap<String, Object> params = new HashMap<String, Object>();
            queueParams.put("x-message-ttl", 6000);
            queueParams.put("x-dead-letter-exchange", "exchange.dlx");
            queueParams.put("x-dead-letter--routing-key", "dlx-routing-key");
            channel.queueDeclare("queue.normal", true, false, false, params);
            channel.queueBind("queue.normal", "exchange.normal", "");


            AMQP.BasicProperties.Builder builders = new AMQP.BasicProperties.Builder();
            builders.priority(5);
            AMQP.BasicProperties propertiess = builders.build();
            channel.basicPublish("exchange_priority", "rk_priority", propertiess, "Messages".getBytes());

            String queue = channel.queueDeclare().getQueue();
            AMQP.BasicProperties properties1 = new AMQP.BasicProperties.Builder().replyTo(queue).build();
            channel.basicPublish("", "rpc_queue", properties1, "message".getBytes());


            channel.txSelect();
            channel.basicPublish("exchange", "routing_key", MessageProperties.PERSISTENT_TEXT_PLAIN, "message".getBytes());
            channel.txCommit();

        } catch (ShutdownSignalException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        final TreeSet<Long> confirmSet = new TreeSet<Long>();
        Connection connect = ConnectMethod2.getConnect();
        Channel channel = connect.createChannel();
        try {
            channel.txSelect();
            channel.basicPublish("exchange", "routing_key", MessageProperties.PERSISTENT_TEXT_PLAIN, "message".getBytes());
            channel.txCommit();

            channel.confirmSelect();
            channel.basicPublish("exchange", "routing_key", MessageProperties.PERSISTENT_TEXT_PLAIN, "message".getBytes());
            if (!channel.waitForConfirms()) {
                System.out.println("send message failed");
            }


            channel.confirmSelect();
            if (!channel.waitForConfirms()) {
                System.out.println("send message failed");
            }
            int msgCount = 0;
            while (true) {
                channel.basicPublish("exchange", "routing_key",
                        MessageProperties.PERSISTENT_TEXT_PLAIN, "message".getBytes());
                // 缓存发出去的消息
                if (++msgCount > 100) {
                    msgCount = 0;
                    try {
                        if (channel.waitForConfirms()) {
                            // 清空缓存的消息
                        }
                    } catch (Exception e) {
                        // 重新发送缓存消息
                    }
                }
            }
        } catch (Exception e) {
            channel.txRollback();
        }
    }


    private static class ConnectMethod1 {
        public static Connection getConnect() {
            try {
                ConnectionFactory factory = new ConnectionFactory();
                factory.setUsername("");
                factory.setPassword("");
                factory.setVirtualHost("/");
                factory.setHost("");
                factory.setPort(5762);
                return factory.newConnection();
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (TimeoutException e) {
                throw new RuntimeException(e);
            }
        }
    }


    private static class ConnectMethod2 {
        public static Connection getConnect() {
            try {
                ConnectionFactory factory = new ConnectionFactory();
                factory.setUri("amqp://userName:password@ipAddress:portNumber/virtualHost");
                return factory.newConnection();
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (KeyManagementException e) {
                throw new RuntimeException(e);
            } catch (TimeoutException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
