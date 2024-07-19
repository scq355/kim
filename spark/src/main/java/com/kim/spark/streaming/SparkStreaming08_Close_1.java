package com.kim.spark.streaming;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.spark.SparkConf;
import org.apache.spark.streaming.Duration;
import org.apache.spark.streaming.StreamingContextState;
import org.apache.spark.streaming.api.java.JavaReceiverInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class SparkStreaming08_Close_1 {
    public static void main(String[] args) throws InterruptedException {
        SparkConf conf = new SparkConf();
        conf.setMaster("local[*]");
        conf.setAppName("SparkStreaming");

        JavaStreamingContext context = new JavaStreamingContext(conf, Duration.apply(3 * 1000L));

        // nc -lp 9999
        JavaReceiverInputDStream<String> socketDs = context.socketTextStream("192.168.124.104", 9999);

        socketDs.print();

        context.start();


        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    FileSystem fileSystem = FileSystem.get(new URI("hdfs://hadoop104:8020"), new Configuration(), "root");
                    while (true) {
                        Thread.sleep(5 * 1000L);
                        // MySQL/Redis/Config
                        boolean flag = fileSystem.exists(new Path("hdfs://hadoop104:8020/stopSpark"));
                        if (flag) {
                            if (context.getState() == StreamingContextState.ACTIVE) {
                                //                context.close(); // 强制关闭
                                context.stop(true, true); // 优雅关闭
                                System.exit(0);
                            }
                        }
                    }
                } catch (IOException | URISyntaxException | InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();

        context.awaitTermination();
    }
}
