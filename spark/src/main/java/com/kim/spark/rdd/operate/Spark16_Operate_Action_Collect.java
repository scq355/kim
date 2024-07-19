package com.kim.spark.rdd.operate;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;

import java.util.Arrays;

public class Spark16_Operate_Action_Collect {
    public static void main(String[] args) {
        SparkConf conf = new SparkConf();
        conf.setMaster("local[*]");
        conf.setAppName("spark");

        JavaSparkContext context = new JavaSparkContext(conf);
        context.parallelize(Arrays.asList(1, 2, 3), 2)
                .map(value -> value * 2)
                .collect() // 行动算子，触发job执行，
                // 将executor执行结果拉取到driver端，将结果组合成集合对象
                .forEach(System.out::println);

        context.close();
    }
}
