package com.kim.spark.sql;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SaveMode;
import org.apache.spark.sql.SparkSession;

public class SparkSQL04_Source_Csv_1 {

    public static void main(String[] args) {
        SparkSession session = SparkSession.builder()
                .master("local[*]")
                .appName("SparkSQL")
                .getOrCreate();

        Dataset<Row> csv = session.read()
                .option("header", "true")
                .option("sep", ",")
                .csv("data/user.csv");
        csv.write()
                .option("header", "true")
                .mode(SaveMode.Overwrite)
                .csv("output");

        session.close();
    }
}
