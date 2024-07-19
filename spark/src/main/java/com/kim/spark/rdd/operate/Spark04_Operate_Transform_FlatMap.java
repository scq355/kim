package com.kim.spark.rdd.operate;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Spark04_Operate_Transform_FlatMap {
    public static void main(String[] args) {
        SparkConf conf = new SparkConf();
        conf.setMaster("local[*]");
        conf.setAppName("spark");

        JavaSparkContext context = new JavaSparkContext(conf);

        List<List<Integer>> datas = Arrays.asList(
                Arrays.asList(1, 2),
                Arrays.asList(3, 4)
        );

        context.parallelize(datas, 2)
                .flatMap(new FlatMapFunction<List<Integer>, Integer>() {
                    @Override
                    public Iterator<Integer> call(List<Integer> list) throws Exception {
                        List<Integer> nums = new ArrayList<>();
                        list.forEach(e -> nums.add(e * 2));
                        return nums.iterator();
                    }
                })
                .collect()
                .forEach(System.out::println);

        context.close();
    }
}
