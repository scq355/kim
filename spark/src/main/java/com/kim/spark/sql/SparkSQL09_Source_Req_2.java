package com.kim.spark.sql;

import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.functions;

public class SparkSQL09_Source_Req_2 {

    public static void main(String[] args) {
        System.setProperty("HADOOP_USER_NAME", "root");

        SparkSession session = SparkSession.builder()
                .enableHiveSupport()
                .master("local[*]")
                .appName("SparkSQL")
                .getOrCreate();

        session.udf().register("cityRemark", functions.udaf(new MyCityRemark(), Encoders.STRING()));

        session.sql("" +
                "SELECT\n" +
                "    *\n" +
                "FROM (\n" +
                "    SELECT\n" +
                "        *,\n" +
                "        rank() OVER (PARTITION BY area ORDER BY click_cnt DESC) rk\n" +
                "    FROM (\n" +
                "        SELECT\n" +
                "            area,\n" +
                "            product_name,\n" +
                "            count(*) click_cnt,\n" +
                "            cityRemark(city_name) city_remark\n" +
                "        FROM (\n" +
                "            SELECT\n" +
                "                click_product_id,\n" +
                "                city_id\n" +
                "            FROM user_visit_action WHERE click_product_id != -1\n" +
                "        ) a\n" +
                "        JOIN (\n" +
                "            SELECT\n" +
                "                product_id,\n" +
                "                product_name\n" +
                "            FROM product_info\n" +
                "        ) p ON a.click_product_id = p.product_id\n" +
                "        JOIN (\n" +
                "            SELECT\n" +
                "                city_id,\n" +
                "                area,\n" +
                "                city_name\n" +
                "            FROM city_info\n" +
                "        ) c ON a.city_id = c.city_id\n" +
                "        GROUP BY area, product_id, product_name\n" +
                "    ) t\n" +
                ") t0 WHERE rk <= 3;"
        ).show(false);

        session.close();
    }
}
