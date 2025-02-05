package com.kim.spark.sql;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

public class SparkSQL02_Model_2 {

    public static void main(String[] args) {
        SparkSession session = SparkSession.builder()
                .master("local[*]")
                .appName("SparkSQL")
                .getOrCreate();

        Dataset<Row> ds = session.read().json("data/user.json");

        // 模型对象访问
        ds.createOrReplaceTempView("user");

        session.sql("select * from user").show();

        session.close();
    }
}
