package com.kim.spark.rdd.operate;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;

import java.util.Arrays;

public class Spark07_Operate_Transform_Sortby {
    public static void main(String[] args) throws Exception {
        SparkConf conf = new SparkConf();
        conf.setMaster("local[*]");
        conf.setAppName("spark");

        JavaSparkContext context = new JavaSparkContext(conf);
        context.parallelize(Arrays.asList(1, 11, 23, 21, 36, 3), 2)
                .sortBy(num -> "" + num, true, 2) // 排序规则
                .collect()
                .forEach(System.out::println);

        context.close();
    }
}
