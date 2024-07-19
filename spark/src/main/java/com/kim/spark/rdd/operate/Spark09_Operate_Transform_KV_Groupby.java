package com.kim.spark.rdd.operate;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Tuple2;

import java.util.Arrays;
import java.util.Iterator;

public class Spark09_Operate_Transform_KV_Groupby {
    public static void main(String[] args) throws Exception {
        SparkConf conf = new SparkConf();
        conf.setMaster("local[*]");
        conf.setAppName("spark");

        JavaSparkContext context = new JavaSparkContext(conf);

        JavaRDD<Integer> rdd = context.parallelize(Arrays.asList(1, 2, 3, 4));
        JavaPairRDD<Integer, Iterable<Integer>> pairRDD = rdd.groupBy(num -> num % 2);
        pairRDD.mapValues(it -> {
            int sum = 0;
            for (Integer num : it) {
                sum += num;
            }
            return sum;
        }).collect().forEach(System.out::println);

        context.close();
    }
}
