package com.kim.spark.rdd.operate;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Tuple2;

import java.util.Arrays;
import java.util.Map;

public class Spark17_Operate_Action_1 {
    public static void main(String[] args) {
        SparkConf conf = new SparkConf();
        conf.setMaster("local[*]");
        conf.setAppName("spark");

        JavaSparkContext context = new JavaSparkContext(conf);
        JavaRDD<Integer> rdd = context.parallelize(Arrays.asList(4, 6, 2, 3, 4, 2), 2);

        Map<Integer, Long> map = rdd.mapToPair(num -> new Tuple2<>(num, num))
                .countByKey();

        System.out.println(map);
        context.close();
    }
}
