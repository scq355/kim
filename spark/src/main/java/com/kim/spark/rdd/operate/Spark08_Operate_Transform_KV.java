package com.kim.spark.rdd.operate;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Tuple2;

import java.util.Arrays;

public class Spark08_Operate_Transform_KV {
    public static void main(String[] args) throws Exception {
        SparkConf conf = new SparkConf();
        conf.setMaster("local[*]");
        conf.setAppName("spark");

        JavaSparkContext context = new JavaSparkContext(conf);
        Tuple2<String, Integer> a = new Tuple2<>("a", 1);
        Tuple2<String, Integer> b = new Tuple2<>("b", 2);
        Tuple2<String, Integer> c = new Tuple2<>("c", 3);

//        context.parallelize(Arrays.asList(a, b, c))
//                .map(t -> new Tuple2<>(t._1, t._2 * 2))
//                .collect()
//                .forEach(System.out::println);

        JavaPairRDD<String, Integer> pairRDD = context.parallelizePairs(Arrays.asList(a, b, c));
        pairRDD.mapValues(v -> v * 2).collect().forEach(System.out::println);


        context.close();
    }
}
