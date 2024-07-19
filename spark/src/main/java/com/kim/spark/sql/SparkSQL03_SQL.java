package com.kim.spark.sql;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

public class SparkSQL03_SQL {

    public static void main(String[] args) {
        SparkSession session = SparkSession.builder()
                .master("local[*]")
                .appName("SparkSQL")
                .getOrCreate();

        Dataset<Row> ds = session.read().json("data/user.json");

        ds.createOrReplaceTempView("user");

        String sql = "select avg(age) from user";
        Dataset<Row> sqlDs = session.sql(sql);
        sqlDs.show();

        session.close();
    }
}
