package com.kim.spark.sql;

import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import org.apache.spark.sql.SparkSession;

public class SparkSQL01_Env {

    public static void main(String[] args) {

        SparkConf conf = new SparkConf();
        conf.setMaster("local[*]");
        conf.setAppName("SparkSQL");
        SparkContext context = new SparkContext(conf);
        SparkSession session = new SparkSession(context);

        session.close();
    }
}
