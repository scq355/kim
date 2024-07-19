package com.kim.spark.sql;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SaveMode;
import org.apache.spark.sql.SparkSession;

import java.util.Properties;

public class SparkSQL05_Source_Json {

    public static void main(String[] args) {
        SparkSession session = SparkSession.builder()
                .master("local[*]")
                .appName("SparkSQL")
                .getOrCreate();


        Properties properties = new Properties();
        properties.setProperty("user", "root");
        properties.setProperty("password", "scq355");
        properties.setProperty("useUnicode", "true");
        properties.setProperty("characterEncoding", "utf-8");
        properties.setProperty("useSSL", "false");

        Dataset<Row> jdbc = session.read().jdbc("jdbc:mysql://192.168.124.108:3306/ms500",
                "app_info",
                properties);

        jdbc.write()
                .mode(SaveMode.Append)
                .jdbc("jdbc:mysql://192.168.124.108:3306/ms500","app_info_test", properties);

        jdbc.show();


        session.close();
    }
}


