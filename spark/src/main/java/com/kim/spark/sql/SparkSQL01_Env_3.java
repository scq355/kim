package com.kim.spark.sql;

import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SparkSession;

import java.util.Arrays;

public class SparkSQL01_Env_3 {

    public static void main(String[] args) {
        SparkConf conf = new SparkConf();
        conf.setMaster("local[*]");
        conf.setAppName("SparkSQL");
        SparkSession session = SparkSession.builder()
                .config(conf)
                .getOrCreate();


        SparkSession session1 = new SparkSession(new SparkContext());

        SparkContext context = session.sparkContext();

        JavaSparkContext jsc = new JavaSparkContext(context);

        jsc.parallelize(Arrays.asList(1, 3, 4, 5));
        session.close();
    }
}
