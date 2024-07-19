package com.kim.spark.sql;

import org.apache.spark.sql.SparkSession;

public class SparkSQL08_Source_Hive {

    public static void main(String[] args) {
        System.setProperty("HADOOP_USER_NAME", "root");

        SparkSession session = SparkSession.builder()
                .enableHiveSupport()
                .master("local[*]")
                .appName("SparkSQL")
                .getOrCreate();

        session.sql("show tables").show();

        session.sql("insert into student values (16, 'nnn')");

        session.sql("select * from student").show();

        session.close();
    }
}
