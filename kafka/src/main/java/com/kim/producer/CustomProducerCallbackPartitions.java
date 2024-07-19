package com.kim.producer;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

/**
 * 指定分区方式
 */
public class CustomProducerCallbackPartitions {
    public static void main(String[] args) throws InterruptedException {
        // 配置
        Properties properties = new Properties();

        // 链接集群
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "hadoop102:9092,hadoop103:9092,hadoop104:9092");
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        // 关联自定义分区
        properties.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, MyPartitioner.class);

        // 创建kafka生产者对象
        KafkaProducer<String, String> kafkaProducer = new KafkaProducer<>(properties);
        // 发送数据
        for (int i = 0; i < 50; i++) {
            // 指定分区
//            kafkaProducer.send(new ProducerRecord<>("first_3", 0,"", "hello" + i), new Callback() {
            // 指定key，用key的hashcode取模分区数
//            kafkaProducer.send(new ProducerRecord<>("first_3", "a", "hello" + i), new Callback() {
            // 都不指定，粘性分区
            kafkaProducer.send(new ProducerRecord<>("first_3", "hello" + i), new Callback() {
                @Override
                public void onCompletion(RecordMetadata metadata, Exception exception) {
                    if (exception == null) {
                        System.out.println("topic=" + metadata.topic() + " 分区="  + metadata.partition());
                    }else {
                        exception.printStackTrace();
                    }
                }
            });
            Thread.sleep(2);
        }
        // 关闭资源
        kafkaProducer.close();
    }
}
