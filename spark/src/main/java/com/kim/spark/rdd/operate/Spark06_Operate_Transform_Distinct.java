package com.kim.spark.rdd.operate;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;

import java.util.Arrays;

public class Spark06_Operate_Transform_Distinct {
    public static void main(String[] args) throws Exception {
        SparkConf conf = new SparkConf();
        conf.setMaster("local[*]");
        conf.setAppName("spark");

        JavaSparkContext context = new JavaSparkContext(conf);
        context.parallelize(Arrays.asList(1, 1, 2, 2, 3, 3), 2)
                .distinct()
                .collect()
                .forEach(System.out::println);

        Thread.sleep(1000000000L);

        context.close();
    }
}
