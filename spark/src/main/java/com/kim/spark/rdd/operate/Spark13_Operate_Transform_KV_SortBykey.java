package com.kim.spark.rdd.operate;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function2;
import scala.Tuple2;

import java.util.Arrays;

public class Spark13_Operate_Transform_KV_SortBykey {
    public static void main(String[] args) throws Exception {
        SparkConf conf = new SparkConf();
        conf.setMaster("local[*]");
        conf.setAppName("spark");

        JavaSparkContext context = new JavaSparkContext(conf);

        context.parallelizePairs(
                        Arrays.asList(
                                new Tuple2<>("a", 12),
                                new Tuple2<>("b", 21),
                                new Tuple2<>("a", 36),
                                new Tuple2<>("b", 4)
                        )
                )
                .sortByKey()
                .collect()
                .forEach(System.out::println);
        context.close();
    }
}
