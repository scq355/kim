package com.kim.spark.streaming;

import org.apache.spark.SparkConf;
import org.apache.spark.streaming.Duration;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaPairDStream;
import org.apache.spark.streaming.api.java.JavaReceiverInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import scala.Tuple2;

import java.util.Arrays;

public class SparkStreaming04_Function {
    public static void main(String[] args) throws InterruptedException {
        SparkConf conf = new SparkConf();
        conf.setMaster("local[*]");
        conf.setAppName("SparkStreaming");

        JavaStreamingContext context = new JavaStreamingContext(conf, Duration.apply(3 * 1000L));

        // nc -lp 9999
        JavaReceiverInputDStream<String> socketDs = context.socketTextStream("192.168.124.104", 9999);

        JavaDStream<String> flatDs = socketDs.flatMap(line -> Arrays.stream(line.split(" ")).iterator());

        JavaPairDStream<String, Integer> pairDStream = flatDs.mapToPair(word -> new Tuple2<>(word, 1));

        JavaPairDStream<String, Integer> countDs = pairDStream.reduceByKey(Integer::sum);

        countDs.foreachRDD(
                rdd -> {
                    rdd.sortByKey().collect().forEach(System.out::println);
                }
        );

        context.start();
        context.awaitTermination();
    }
}
