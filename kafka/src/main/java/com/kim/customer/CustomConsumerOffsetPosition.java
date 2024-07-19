package com.kim.customer;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.*;

public class CustomConsumerOffsetPosition {

    public static void main(String[] args) {
        Properties properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "hadoop102:9092");

        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "TEST_1");

        // 创建消费者
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties);

        // 订阅主题对应的分区
        ArrayList<TopicPartition> topics = new ArrayList<>();
        topics.add(new TopicPartition("first", 0));
        consumer.assign(topics);

        // 指定位置消费
        Set<TopicPartition> assignment = consumer.assignment();

        // 保证分区方案已经制定完成
        while (assignment.size() == 0) {
            consumer.poll(Duration.ofSeconds(1));

            assignment = consumer.assignment();
        }

        HashMap<TopicPartition, Long > topicPartitionOffsetAndTimestamp = new HashMap<>();
        for (TopicPartition topicPartition : assignment) {
            topicPartitionOffsetAndTimestamp.put(topicPartition, System.currentTimeMillis() - 24 * 3600 * 1000L);
        }

        Map<TopicPartition, OffsetAndTimestamp> timestampMap = consumer.offsetsForTimes(topicPartitionOffsetAndTimestamp);

        for (TopicPartition partition : assignment) {
            OffsetAndTimestamp offsetAndTimestamp = timestampMap.get(partition);
            consumer.seek(partition, offsetAndTimestamp.offset());
         }

        // 消费数据
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(1));
            for (ConsumerRecord<String, String> record : records) {
                System.out.println(record.toString());
            }
        }
    }
}
