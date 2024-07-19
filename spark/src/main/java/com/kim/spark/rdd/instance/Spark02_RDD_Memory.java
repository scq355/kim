package com.kim.spark.rdd.instance;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import java.util.Arrays;
import java.util.List;

public class Spark02_RDD_Memory {

    public static void main(String[] args) {
        SparkConf conf = new SparkConf();
        conf.setMaster("local[*]");
        conf.setAppName("Spark01_Env");

        JavaSparkContext context = new JavaSparkContext(conf);

        List<String> list = Arrays.asList("scq", "pwd", "utf");
        JavaRDD<String> rdd = context.parallelize(list);
        List<String> collect = rdd.collect();
        collect.forEach(System.out::println);

        context.close();
    }
}
