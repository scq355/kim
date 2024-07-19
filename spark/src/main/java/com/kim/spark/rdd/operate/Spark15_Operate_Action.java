package com.kim.spark.rdd.operate;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;

import java.util.Arrays;

public class Spark15_Operate_Action {
    public static void main(String[] args) {
        SparkConf conf = new SparkConf();
        conf.setMaster("local[*]");
        conf.setAppName("spark");

        JavaSparkContext context = new JavaSparkContext(conf);
        context.parallelize(Arrays.asList(1, 2, 3), 2)
                .map(value -> value * 2)
                .collect() // 行动算子，触发作业执行
                .forEach(System.out::println);

        context.close();
    }
}
