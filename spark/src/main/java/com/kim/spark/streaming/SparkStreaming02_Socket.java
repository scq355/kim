package com.kim.spark.streaming;

import org.apache.spark.SparkConf;
import org.apache.spark.streaming.Duration;
import org.apache.spark.streaming.api.java.JavaReceiverInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;

public class SparkStreaming02_Socket {
    public static void main(String[] args) throws InterruptedException {
        SparkConf conf = new SparkConf();
        conf.setMaster("local[*]");
        conf.setAppName("SparkStreaming");

        JavaStreamingContext context = new JavaStreamingContext(conf, Duration.apply(3 * 1000L));

        // nc -lp 9999
        JavaReceiverInputDStream<String> socketDs = context.socketTextStream("192.168.124.104", 9999);
        socketDs.print();

        context.start();
        context.awaitTermination();
    }
}
