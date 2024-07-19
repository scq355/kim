package com.kim.spark.dep;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Tuple2;

import java.util.Arrays;
import java.util.List;

public class Spark06_Persist {
    public static void main(String[] args) throws Exception {
        SparkConf conf = new SparkConf();
        conf.setMaster("local[*]");
        conf.setAppName("spark");

        JavaSparkContext context = new JavaSparkContext(conf);
        // 推荐hdfs，也可以本地
        context.setCheckpointDir("checkpoint/");

        List<Tuple2<String, Integer>> datas = Arrays.asList(
                new Tuple2<>("a", 1),
                new Tuple2<>("b", 2),
                new Tuple2<>("a", 3),
                new Tuple2<>("b", 4)
        );

        JavaRDD<Tuple2<String, Integer>> rdd = context.parallelize(datas, 2);
        JavaPairRDD<String, Integer> pairRDD = rdd.mapToPair(kv -> kv);
        // cache会在血缘关系中增加依赖关系
        // rdd.cache();
        // checkpoint会切断（改变）血缘
        rdd.checkpoint();

        JavaPairRDD<String, Integer> countRdd = pairRDD.reduceByKey(Integer::sum);
        System.out.println(countRdd.toDebugString());
        countRdd.collect();
        System.out.println("******************************");
        System.out.println(countRdd.toDebugString());

        context.close();
    }
}
