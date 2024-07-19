package com.kim.spark.rdd.operate;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Tuple1;
import scala.Tuple2;

import java.util.Arrays;

public class Spark01_Operate {
    public static void main(String[] args) {
        SparkConf conf = new SparkConf();
        conf.setMaster("local[*]");
        conf.setAppName("spark");

        JavaSparkContext context = new JavaSparkContext(conf);
        JavaRDD<Integer> rdd = context.parallelize(Arrays.asList(1, 2, 3));

        Tuple1<String> abc = new Tuple1<>("abc");
        Tuple2<String, String> abc1 = new Tuple2<>("a", "1");
        System.out.println(abc1._1);
        System.out.println(abc1._2());

        context.close();
    }
}
