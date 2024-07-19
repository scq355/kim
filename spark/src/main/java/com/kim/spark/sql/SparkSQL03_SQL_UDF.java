package com.kim.spark.sql;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.api.java.UDF1;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StringType$;

public class SparkSQL03_SQL_UDF {

    public static void main(String[] args) {
        SparkSession session = SparkSession.builder()
                .master("local[*]")
                .appName("SparkSQL")
                .getOrCreate();

        Dataset<Row> ds = session.read().json("data/user.json");

        ds.createOrReplaceTempView("user");
//        String sql = "select concat('Name:', name) from user";

        session.udf().register(
                "prefixName",
                new UDF1<String, String>() {
                    @Override
                    public String call(String name) throws Exception {
                        return "name:" + name;
                    }
                },
//                StringType$.MODULE$);
                DataTypes.StringType);

        String sql = "select prefixName(name) from user";
        Dataset<Row> sqlDs = session.sql(sql);
        sqlDs.show();

        session.close();
    }
}
