package com.kim.spark.sql;

import org.apache.spark.sql.*;

public class SparkSQL03_SQL_UDAF {

    public static void main(String[] args) {
        SparkSession session = SparkSession.builder()
                .master("local[*]")
                .appName("SparkSQL")
                .getOrCreate();

        Dataset<Row> ds = session.read().json("data/user.json");

        ds.createOrReplaceTempView("user");

        session.udf().register(
                "avgAge",
                functions.udaf(new MyAvgAgeUDAF(), Encoders.LONG()));

        String sql = "select avgAge(age) from user";
        Dataset<Row> sqlDs = session.sql(sql);
        sqlDs.show();

        session.close();
    }
}

