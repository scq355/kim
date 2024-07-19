package com.kim.spark.dep;

import lombok.Data;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Serializable;

import java.util.Arrays;

public class Spark09_Broadcast {
    public static void main(String[] args) throws Exception {
        SparkConf conf = new SparkConf();
        conf.setMaster("local[*]");
        conf.setAppName("spark");

        JavaSparkContext context = new JavaSparkContext(conf);

        JavaRDD<Integer> rdd = context.parallelize(Arrays.asList(1, 2, 3, 4));

        // driver端创建
        MyValue mv = new MyValue();

        // executor端执行，结果不会拉取到driver端
        rdd.foreach(v -> mv.setSum(mv.getSum() + v));

        // driver端执行
        System.out.println(mv.getSum());

        context.close();
    }
}

@Data
class MyValue implements Serializable {
    private int sum = 0;
}
