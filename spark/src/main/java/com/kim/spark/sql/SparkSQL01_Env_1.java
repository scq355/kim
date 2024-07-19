package com.kim.spark.sql;

import org.apache.spark.sql.SparkSession;

public class SparkSQL01_Env_1 {

    public static void main(String[] args) {
        SparkSession session = SparkSession.builder()
                .master("local[*]")
                .appName("SparkSQL")
                .getOrCreate();
        session.close();
    }
}
