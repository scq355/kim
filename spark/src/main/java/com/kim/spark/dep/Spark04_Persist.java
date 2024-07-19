package com.kim.spark.dep;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.storage.StorageLevel;
import scala.Tuple2;

import java.util.Arrays;
import java.util.List;

public class Spark04_Persist {
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

        JavaPairRDD<String, Integer> rdd = context.parallelizePairs(datas);
        rdd.persist(StorageLevel.MEMORY_AND_DISK());

        JavaPairRDD<String, Integer> countRdd = rdd.reduceByKey(Integer::sum);
        countRdd.collect().forEach(System.out::println);

        JavaPairRDD<String, Iterable<Integer>> groupRdd = rdd.groupByKey();
        groupRdd.collect().forEach(System.out::println);

        context.close();
    }
}
