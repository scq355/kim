package com.kim.spark.rdd.operate;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import java.util.Arrays;
import java.util.List;

public class Spark17_Operate_Action {
    public static void main(String[] args) {
        SparkConf conf = new SparkConf();
        conf.setMaster("local[*]");
        conf.setAppName("spark");

        JavaSparkContext context = new JavaSparkContext(conf);
        JavaRDD<Integer> rdd = context.parallelize(Arrays.asList(1, 2, 3), 2);

        List<Integer> collect = rdd.collect();
        long count = rdd.count();
        Integer first = rdd.first();
        List<Integer> take = rdd.take(3);

        System.out.println(collect);
        System.out.println(count);
        System.out.println(first);
        System.out.println(take);

        context.close();
    }
}
