package com.kim.spark.sql;

import org.apache.spark.sql.SparkSession;

public class SparkSQL09_Source_Req_1 {

    public static void main(String[] args) {
        System.setProperty("HADOOP_USER_NAME", "root");

        SparkSession session = SparkSession.builder()
                .enableHiveSupport()
                .master("local[*]")
                .appName("SparkSQL")
                .getOrCreate();

        session.sql("show tables").show();

        session.sql("" +
                "SELECT c.area, p.product_name, count(*) as num, cityRemark(c.city_name) \n" +
                "FROM (SELECT * FROM user_visit_action WHERE click_product_id != -1) a\n" +
                "         JOIN (SELECT * FROM product_info) p ON a.click_product_id = p.product_id\n" +
                "         JOIN (SELECT city_id, area, city_name FROM city_info) c ON a.city_id = c.city_id\n" +
                "GROUP BY c.area, p.product_name;"
        ).show();

        session.close();
    }
}
