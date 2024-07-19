package com.kim.spark.dep;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.broadcast.Broadcast;

import java.util.Arrays;
import java.util.List;

public class Spark09_Broadcast_1 {
    public static void main(String[] args) throws Exception {
        SparkConf conf = new SparkConf();
        conf.setMaster("local[*]");
        conf.setAppName("spark");

        JavaSparkContext context = new JavaSparkContext(conf);

        JavaRDD<String> rdd = context.parallelize(Arrays.asList("Hadoop", "Flink", "Spark", "Hive", "Flume"));

        // executor从driver拉取数据单位是task
        List<String> data = Arrays.asList("Hadoop", "Flink");
        // 以executor为单位拉取
        Broadcast<List<String>> broadcast = context.broadcast(data);

        JavaRDD<String> filterRdd = rdd.filter(broadcast.getValue()::contains);

        filterRdd.collect().forEach(System.out::println);

        context.close();
    }
}

