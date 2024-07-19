package com.kim.spark.dep;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Tuple2;

import java.util.Arrays;
import java.util.List;

public class Spark07_Persist {
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
        JavaPairRDD<String, Integer> pairRDD = rdd.mapToPair(kv -> {
            System.out.println("******************************");
            return kv;
        });
        // cache会在血缘关系中增加依赖关系
        // rdd.cache();
        // checkpoint会切断（改变）血缘
        rdd.checkpoint();

        // shuffle算子自动含有缓存
//        JavaPairRDD<String, Integer> reduceRdd = pairRDD.reduceByKey(Integer::sum);
//        reduceRdd.groupByKey().collect();
//        reduceRdd.sortByKey().collect();

        // 若重复调用相同规则的shuffle算子，第二次shuffle算子不会有shuffle操作
        JavaPairRDD<String, Integer> reduceRdd = pairRDD.reduceByKey(Integer::sum);
        reduceRdd.reduceByKey(Integer::sum).collect();
        System.out.println("计算完毕");

        Thread.sleep(10000000L);

        context.close();
    }
}
