package com.kim.spark.rdd.operate;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import java.util.Arrays;
import java.util.List;

public class Spark03_Operate_Transform_Filter {
    public static void main(String[] args) {
        SparkConf conf = new SparkConf();
        conf.setMaster("local[*]");
        conf.setAppName("spark");

        JavaSparkContext context = new JavaSparkContext(conf);
        context.parallelize(Arrays.asList(1, 2, 3), 2)
                .filter(e -> e > 2)
                .collect()
                .forEach(System.out::println);

        context.close();
    }
}
