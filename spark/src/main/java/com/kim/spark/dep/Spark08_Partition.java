package com.kim.spark.dep;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Tuple2;

import java.util.Arrays;
import java.util.List;

public class Spark08_Partition {
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

        JavaRDD<Tuple2<String, Integer>> rdd = context.parallelize(datas, 3);
        JavaPairRDD<String, Integer> mapRdd = rdd.mapToPair(kv -> {
            System.out.println("******************************");
            return kv;
        });

        // 数据分区规则：
        mapRdd.reduceByKey(Integer::sum).saveAsTextFile("out");

        context.close();
    }
}
