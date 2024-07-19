package com.kim.spark.sql;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

public class SparkSQL05_Source_Json_1 {

    public static void main(String[] args) {
        SparkSession session = SparkSession.builder()
                .master("local[*]")
                .appName("SparkSQL")
                .getOrCreate();

        Dataset<Row> json = session.read()
                .option("header", "true")
                .csv("data/user.csv");
        json.write().json("out_json");
        json.show();

        session.close();
    }
}
