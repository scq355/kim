package com.kim.spark.rdd.instance;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

public class Spark03_RDD_Disk_Partition_Data {

    public static void main(String[] args) {
        SparkConf conf = new SparkConf();
        conf.setMaster("local[*]");
        conf.setAppName("Spark");

        JavaSparkContext context = new JavaSparkContext(conf);
        // 分区数量计算按字节计算；
        // 分区数据存储按行存储（业务数据完整性）；
        // 读取数据时，数据偏移量从0开始，相同偏移量不能重复读取
        JavaRDD<String> rdd = context.textFile("data/test.txt", 2);
        rdd.saveAsTextFile("out");

        context.close();
    }
}
