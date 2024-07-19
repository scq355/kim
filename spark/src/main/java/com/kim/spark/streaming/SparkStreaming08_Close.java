package com.kim.spark.streaming;

import lombok.SneakyThrows;
import org.apache.spark.SparkConf;
import org.apache.spark.streaming.Duration;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaPairDStream;
import org.apache.spark.streaming.api.java.JavaReceiverInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import scala.Tuple2;

import java.util.Arrays;

public class SparkStreaming08_Close {
    public static void main(String[] args) throws InterruptedException {
        SparkConf conf = new SparkConf();
        conf.setMaster("local[*]");
        conf.setAppName("SparkStreaming");

        JavaStreamingContext context = new JavaStreamingContext(conf, Duration.apply(3 * 1000L));

        // nc -lp 9999
        JavaReceiverInputDStream<String> socketDs = context.socketTextStream("192.168.124.104", 9999);

        socketDs.print();

        context.start();

        boolean flag = false;

        new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                while (true) {
                    Thread.sleep(3 * 1000L);
                    if (flag) {
                        //                context.close(); // 强制关闭
                        context.stop(true, true); // 优雅关闭
                    }
                }
            }
        }).start();

        context.awaitTermination();
    }
}
