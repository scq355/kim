package com.kim.spark.rdd.operate;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Tuple2;

import java.util.Arrays;

public class Spark13_Operate_Transform_KV_SortBykey_2 {
    public static void main(String[] args) throws Exception {
        SparkConf conf = new SparkConf();
        conf.setMaster("local[*]");
        conf.setAppName("spark");

        JavaSparkContext context = new JavaSparkContext(conf);

        context.parallelizePairs(
                        Arrays.asList(
                                new Tuple2<>("a", 1),
                                new Tuple2<>("a", 3),
                                new Tuple2<>("a", 4),
                                new Tuple2<>("a", 2)
                        )
                )
                .mapToPair(kv -> new Tuple2<>(kv._2(), kv))
                .sortByKey()
                .map(kv -> kv._2)
                .collect()
                .forEach(System.out::println);
        context.close();
    }
}
