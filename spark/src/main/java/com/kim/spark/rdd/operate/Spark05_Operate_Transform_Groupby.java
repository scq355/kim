package com.kim.spark.rdd.operate;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;

import java.util.Arrays;

public class Spark05_Operate_Transform_Groupby {
    public static void main(String[] args) throws Exception{
        SparkConf conf = new SparkConf();
        conf.setMaster("local[*]");
        conf.setAppName("spark");

        JavaSparkContext context = new JavaSparkContext(conf);
        JavaRDD<Integer> rdd = context.parallelize(Arrays.asList(1, 2, 3, 4, 5, 6), 2);

        rdd.groupBy(new Function<Integer, String>() {
            @Override
            public String call(Integer num) throws Exception {
                if (num / 2 == 0) {
                    return "a";
                }
                return "b";
            }
        }, 3)
                .collect()
                .forEach(System.out::println);

        Thread.sleep(1000000000L);

        context.close();
    }
}
