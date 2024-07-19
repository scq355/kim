package com.kim.spark.rdd.operate;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import java.util.Arrays;
import java.util.Iterator;

public class Spark10_Operate_Transform_KV_WordCount {
    public static void main(String[] args) throws Exception {
        SparkConf conf = new SparkConf();
        conf.setMaster("local[*]");
        conf.setAppName("spark");

        JavaSparkContext context = new JavaSparkContext(conf);

        JavaRDD<String> lineRdd = context.textFile("data/word.txt");
        JavaRDD<String> wordRdd = lineRdd.flatMap(line -> Arrays.asList(line.split(" ")).iterator());
        JavaPairRDD<String, Iterable<String>> pairRDD = wordRdd.groupBy(word -> word);
        JavaPairRDD<String, Integer> wcRdd = pairRDD.mapValues(v -> {
            int num = 0;
            for (String w : v) {
                num++;
            }
            return num;
        });

        wcRdd.collect().forEach(System.out::println);


        context.close();
    }
}
