package com.kim.spark.rdd.operate;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function2;
import scala.Tuple2;

import java.util.Arrays;

public class Spark12_Operate_Transform_KV_WordCount {
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
                .reduceByKey(new Function2<Integer, Integer, Integer>() {
                    @Override
                    public Integer call(Integer num1, Integer num2) throws Exception {
                        return num1 + num2;
                    }
                })
                .collect()
                .forEach(System.out::println);
        context.close();
    }
}
