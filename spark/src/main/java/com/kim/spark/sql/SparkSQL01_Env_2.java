package com.kim.spark.sql;

import org.apache.spark.SparkConf;
import org.apache.spark.sql.SparkSession;

public class SparkSQL01_Env_2 {

    public static void main(String[] args) {
        SparkConf conf = new SparkConf();
        conf.setMaster("local[*]");
        conf.setAppName("SparkSQL");
        SparkSession session = SparkSession.builder()
                .config(conf)
                .getOrCreate();
        session.close();
    }
}
