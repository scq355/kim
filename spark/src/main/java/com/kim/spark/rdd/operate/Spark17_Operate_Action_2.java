package com.kim.spark.rdd.operate;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Tuple2;

import java.util.Arrays;
import java.util.Map;

public class Spark17_Operate_Action_2 {
    public static void main(String[] args) {
        SparkConf conf = new SparkConf();
        conf.setMaster("local[*]");
        conf.setAppName("spark");

        JavaSparkContext context = new JavaSparkContext(conf);
        JavaRDD<Integer> rdd = context.parallelize(Arrays.asList(4, 6, 2, 3, 4, 2), 2);

        JavaPairRDD<Integer, Integer> pairRDD = rdd.mapToPair(num -> new Tuple2<>(num, num));
        pairRDD.saveAsTextFile("out-txt");

        pairRDD.saveAsObjectFile("out-obj");

        context.close();
    }
}
