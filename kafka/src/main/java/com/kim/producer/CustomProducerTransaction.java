package com.kim.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

/**
 * 普通异步发送
 */
public class CustomProducerTransaction {
    public static void main(String[] args) {
        // 配置
        Properties properties = new Properties();

        // 链接集群
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "hadoop102:9092,hadoop103:9092");
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        //指定事务ID
        properties.put(ProducerConfig.TRANSACTIONAL_ID_CONFIG, "TRANSACTIONAL_01");

        // 创建kafka生产者对象
        KafkaProducer<String, String> kafkaProducer = new KafkaProducer<>(properties);

        kafkaProducer.initTransactions();

        kafkaProducer.beginTransaction();
        try {
            // 发送数据
            for (int i = 0; i < 5; i++) {
                kafkaProducer.send(new ProducerRecord<>("first", "hello".concat(i + "")));
            }
            int i = 1 / 0;
            kafkaProducer.commitTransaction();
        } catch (Exception e) {
            kafkaProducer.abortTransaction();
        } finally {
            // 关闭资源
            kafkaProducer.close();
        }


    }
}
