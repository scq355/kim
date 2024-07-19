package com.kim.spark.rdd.operate;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Tuple2;

import java.util.Arrays;

public class Spark11_Operate_Transform_KV_WordCount {
    public static void main(String[] args) throws Exception {
        SparkConf conf = new SparkConf();
        conf.setMaster("local[*]");
        conf.setAppName("spark");

        JavaSparkContext context = new JavaSparkContext(conf);

        context.parallelizePairs(
                        Arrays.asList(
                                new Tuple2<>("a", 1),
                                new Tuple2<>("b", 2),
                                new Tuple2<>("a", 3),
                                new Tuple2<>("b", 4)
                        )
                )
                .groupByKey()
                .mapValues(vs -> {
                    int sum = 0;
                    for (Integer v : vs) {
                        sum += v;
                    }
                    return sum;
                })
                .collect().forEach(System.out::println);
        context.close();
    }
}
