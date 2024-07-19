package com.kim.spark.rdd.operate;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import java.util.Arrays;

public class Spark14_Operate_Transform_Partition {
    public static void main(String[] args) throws Exception {
        SparkConf conf = new SparkConf();
        conf.setMaster("local");
        conf.setAppName("spark");

        JavaSparkContext context = new JavaSparkContext(conf);

        JavaRDD<Integer> rdd = context.parallelize(Arrays.asList(1, 2, 3, 4, 5, 6), 2);
        JavaRDD<Integer> filterRdd = rdd.filter(num -> num % 2 == 0);
        // 可以缩减，但不能直接扩大，若需要扩大，则增加参数true，会有shuffle
//        JavaRDD<Integer> coalesceRdd = filterRdd.coalesce(1, true);
//        coalesceRdd.saveAsTextFile("output");

        // shuffle为true的coalesce
        filterRdd.repartition(3).saveAsTextFile("output1");

        context.close();
    }
}
