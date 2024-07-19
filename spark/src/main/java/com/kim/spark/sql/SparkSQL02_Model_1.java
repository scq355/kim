package com.kim.spark.sql;

import lombok.Data;
import org.apache.spark.api.java.function.ForeachFunction;
import org.apache.spark.rdd.RDD;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import scala.Serializable;

public class SparkSQL02_Model_1 {

    public static void main(String[] args) {
        SparkSession session = SparkSession.builder()
                .master("local[*]")
                .appName("SparkSQL")
                .getOrCreate();

        Dataset<Row> ds = session.read().json("data/user.json");
        RDD<Row> rdd = ds.rdd();
        // Dataframe
        ds.foreach(new ForeachFunction<Row>() {
            @Override
            public void call(Row row) throws Exception {
                System.out.println(row.getString(2));
            }
        });

        // row -> obj
        Dataset<User> userDs = ds.as(Encoders.bean(User.class));
        userDs.foreach(new ForeachFunction<User>() {
            @Override
            public void call(User user) throws Exception {
                System.out.println(user.getName());
            }
        });


        session.close();
    }
}

@Data
class User implements Serializable {
    private int id;
    private int age;
    private String name;
}
