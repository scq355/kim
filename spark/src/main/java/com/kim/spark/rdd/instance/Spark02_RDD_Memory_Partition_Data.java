package com.kim.spark.rdd.instance;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import java.util.Arrays;
import java.util.List;

public class Spark02_RDD_Memory_Partition_Data {

    public static void main(String[] args) {
        SparkConf conf = new SparkConf();
        conf.setMaster("local");
        conf.setAppName("Spark");

        JavaSparkContext context = new JavaSparkContext(conf);

        List<String> list = Arrays.asList("1", "2", "3", "4", "5", "6");

        // 分区数据存储原则：均分（向下取整）
        JavaRDD<String> rdd = context.parallelize(list, 4);
        rdd.saveAsTextFile("out");

        context.close();
    }
}
