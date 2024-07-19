package com.kim.spark.rdd.instance;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

public class Spark03_RDD_Disk_Partition {

    public static void main(String[] args) {
        SparkConf conf = new SparkConf();
        conf.setMaster("local[*]");
        conf.setAppName("Spark01_Env");

        JavaSparkContext context = new JavaSparkContext(conf);

        // minPartitions = math.min(defaultParallelism, 2)
        // math.min(spark.default.partitions, 2)
        // math.min(totalCors, 2)
        JavaRDD<String> rdd = context.textFile("data/test.txt", 1);
        rdd.saveAsTextFile("out");

        context.close();
    }
}
