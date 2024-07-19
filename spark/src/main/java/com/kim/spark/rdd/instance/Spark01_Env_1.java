package com.kim.spark.rdd.instance;

import org.apache.spark.api.java.JavaSparkContext;

public class Spark01_Env_1 {

    public static void main(String[] args) {
        JavaSparkContext context = new JavaSparkContext("local[*]", "Spark01_Env");

        context.close();

    }
}
