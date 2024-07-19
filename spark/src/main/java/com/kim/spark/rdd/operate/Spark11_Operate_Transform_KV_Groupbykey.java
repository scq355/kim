package com.kim.spark.rdd.operate;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Tuple2;

import java.util.Arrays;

public class Spark11_Operate_Transform_KV_Groupbykey {
    public static void main(String[] args) throws Exception {
        SparkConf conf = new SparkConf();
        conf.setMaster("local[*]");
        conf.setAppName("spark");

        JavaSparkContext context = new JavaSparkContext(conf);
//        JavaRDD<Tuple2<String, Integer>> rdd = context.parallelize(
//                Arrays.asList(
//                        new Tuple2<>("a", 1),
//                        new Tuple2<>("b", 2),
//                        new Tuple2<>("a", 3),
//                        new Tuple2<>("b", 4)
//                )
//        );
//        JavaPairRDD<String, Iterable<Tuple2<String, Integer>>> groupRDD = rdd.groupBy(
//                t -> t._1
//        );
//        groupRDD.collect().forEach(System.out::println);

        context.parallelizePairs(
                        Arrays.asList(
                                new Tuple2<>("a", 1),
                                new Tuple2<>("b", 2),
                                new Tuple2<>("a", 3),
                                new Tuple2<>("b", 4)
                        )
                )
                .groupByKey()
                .collect().forEach(System.out::println);


        context.close();
    }
}
