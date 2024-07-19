package com.kim.spark.streaming;

import org.apache.spark.SparkConf;
import org.apache.spark.streaming.Duration;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaPairDStream;
import org.apache.spark.streaming.api.java.JavaReceiverInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import scala.Tuple2;

import java.util.Arrays;

public class SparkStreaming06_Window {
    public static void main(String[] args) throws InterruptedException {
        SparkConf conf = new SparkConf();
        conf.setMaster("local[*]");
        conf.setAppName("SparkStreaming");

        JavaStreamingContext context = new JavaStreamingContext(conf, Duration.apply(3 * 1000L));

        // nc -lp 9999
        JavaReceiverInputDStream<String> socketDs = context.socketTextStream("192.168.124.104", 9999);

        JavaDStream<String> flatDs = socketDs.flatMap(line -> Arrays.stream(line.split(" ")).iterator());

        JavaPairDStream<String, Integer> pairDStream = flatDs.mapToPair(word -> new Tuple2<>(word, 1));

        // 窗口：改变窗口的数据范围
        JavaPairDStream<String, Integer> windowDs = pairDStream.window(Duration.apply(6 * 1000L), Duration.apply(6 * 1000L));
        JavaPairDStream<String, Integer> countDs = windowDs.reduceByKey(Integer::sum);

        countDs.print();

        context.start();
        context.awaitTermination();
    }
}
