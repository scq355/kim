package com.kim.spark.streaming;

import com.google.common.collect.Maps;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.streaming.Duration;
import org.apache.spark.streaming.api.java.JavaInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka010.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SparkStreaming03_Kafka {
    public static void main(String[] args) throws InterruptedException {
        SparkConf conf = new SparkConf();
        conf.setMaster("local[*]");
        conf.setAppName("SparkStreaming");

        JavaStreamingContext context = new JavaStreamingContext(conf, Duration.apply(3 * 1000L));

        // nc -lp 9999
        HashMap<String, Object> map = Maps.newHashMap();
        map.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "hadoop104:9092,hadoop105:9092,hadoop106:9092");
        map.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, org.apache.kafka.common.serialization.StringDeserializer.class.getName());
        map.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, org.apache.kafka.common.serialization.StringDeserializer.class.getName());
        map.put(ConsumerConfig.GROUP_ID_CONFIG, "spark");
        map.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");

        List<String> strings = new ArrayList<>();
        strings.add("topic_spark");

        JavaInputDStream<ConsumerRecord<String, String>> directStream = KafkaUtils.createDirectStream(
                context,
                LocationStrategies.PreferBrokers(),
                ConsumerStrategies.Subscribe(strings, map));
        directStream
                .map(new Function<ConsumerRecord<String, String>, String>() {
                    @Override
                    public String call(ConsumerRecord<String, String> v1) throws Exception {
                        return v1.value();
                    }
                })
                .print(100);

        context.start();
        context.awaitTermination();
    }
}
