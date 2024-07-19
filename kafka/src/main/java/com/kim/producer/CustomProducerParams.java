package com.kim.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.record.CompressionType;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

/**
 * 普通异步发送
 */
public class CustomProducerParams {
    public static void main(String[] args) {
        // 配置
        Properties properties = new Properties();

        // 链接集群
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "hadoop102:9092,hadoop103:9092");
        // 序列化
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        // 缓冲区
        properties.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432);
        // 批次
        properties.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384);
        // linger.ms
        properties.put(ProducerConfig.LINGER_MS_CONFIG, 1);
        // 压缩
        properties.put(ProducerConfig.COMPRESSION_TYPE_CONFIG, CompressionType.SNAPPY.name);
        // 幂等
        properties.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true);

        // 创建kafka生产者对象
        KafkaProducer<String, String> kafkaProducer = new KafkaProducer<>(properties);
        // 发送数据
        for (int i = 0; i < 5; i++) {
            kafkaProducer.send(new ProducerRecord<>("first_3", "hello".concat(i + "")));
        }
        // 关闭资源
        kafkaProducer.close();
    }
}
