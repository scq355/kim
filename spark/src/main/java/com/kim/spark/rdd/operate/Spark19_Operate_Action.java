package com.kim.spark.rdd.operate;

import lombok.Data;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import java.util.Arrays;

public class Spark19_Operate_Action {
    public static void main(String[] args) {
        SparkConf conf = new SparkConf();
        conf.setMaster("local[*]");
        conf.setAppName("spark");

        JavaSparkContext context = new JavaSparkContext(conf);
        JavaRDD<String> rdd = context.parallelize(Arrays.asList(
                "Hadoop", "Hive", "Hbase", "Spark", "Flink", "Kafka"
        ), 2);

        // RDD算子的逻辑代码是在Executor端执行，其他代码在Driver端执行
        new Search("H").match(rdd);

        context.close();
    }
}

@Data
class Search {
    private String query;
    public Search(String query) {
        this.query = query;
    }
    public void match(JavaRDD<String> rdd) {
        String q = this.getQuery();
        rdd.filter(s -> s.startsWith(q))
                .collect()
                .forEach(System.out::println);
    }
}

