package com.kim.spark.dep;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Tuple2;

import java.util.Arrays;
import java.util.List;

public class Spark03_Cache {
    public static void main(String[] args) throws Exception {
        SparkConf conf = new SparkConf();
        conf.setMaster("local[*]");
        conf.setAppName("spark");

        JavaSparkContext context = new JavaSparkContext(conf);

        List<Tuple2<String, Integer>> datas = Arrays.asList(
                new Tuple2<>("a", 1),
                new Tuple2<>("b", 2),
                new Tuple2<>("a", 3),
                new Tuple2<>("b", 4)
        );
        // 重复计算
        JavaPairRDD<String, Integer> rdd = context.parallelizePairs(datas);
        JavaPairRDD<String, Integer> countRdd = rdd.reduceByKey(Integer::sum);
        countRdd.collect().forEach(System.out::println);

        JavaPairRDD<String, Integer> rdd1 = context.parallelizePairs(datas);
        JavaPairRDD<String, Iterable<Integer>> groupRdd = rdd1.groupByKey();
        groupRdd.collect().forEach(System.out::println);

        context.close();
    }
}
