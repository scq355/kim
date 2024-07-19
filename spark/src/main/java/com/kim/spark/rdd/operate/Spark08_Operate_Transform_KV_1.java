package com.kim.spark.rdd.operate;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Tuple2;

import java.util.Arrays;

public class Spark08_Operate_Transform_KV_1 {
    public static void main(String[] args) throws Exception {
        SparkConf conf = new SparkConf();
        conf.setMaster("local[*]");
        conf.setAppName("spark");

        JavaSparkContext context = new JavaSparkContext(conf);

        JavaRDD<Integer> rdd = context.parallelize(Arrays.asList(1, 2, 3, 4));
        rdd.mapToPair(num -> new Tuple2<>(num, num * 2))
                .mapValues(v -> v * 2)
                .collect()
                .forEach(System.out::println);


        context.close();
    }
}
