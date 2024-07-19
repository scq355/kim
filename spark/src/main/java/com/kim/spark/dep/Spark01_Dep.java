package com.kim.spark.dep;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Tuple2;

import java.util.Arrays;
import java.util.List;

public class Spark01_Dep {
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
        JavaRDD<Tuple2<String, Integer>> rdd = context.parallelize(datas);
        System.out.println("rdd\t" + rdd.toDebugString());

        JavaPairRDD<String, Integer> pairRDD = rdd.mapToPair(t -> t);
        System.out.println("pairRDD\t" + pairRDD.toDebugString());

        JavaPairRDD<String, Integer> countRdd = pairRDD.reduceByKey(Integer::sum);
        System.out.println("countRdd\t" + countRdd.toDebugString());

        countRdd.collect()
                .forEach(System.out::println);
        context.close();
    }
}
