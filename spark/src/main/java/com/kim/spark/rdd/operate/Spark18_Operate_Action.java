package com.kim.spark.rdd.operate;

import lombok.Data;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Serializable;

import java.util.Arrays;

public class Spark18_Operate_Action {
    public static void main(String[] args) {
        SparkConf conf = new SparkConf();
        conf.setMaster("local[*]");
        conf.setAppName("spark");

        JavaSparkContext context = new JavaSparkContext(conf);
        JavaRDD<Integer> rdd = context.parallelize(Arrays.asList(1,2,3,4,5), 2);

        // Driver端创建
        Student s = new Student();
        // Executor端执行，从driver端通过网络拉取对象，该对象必须序列化，否则无法传递
        rdd.foreach(num -> {
            System.out.println(s.getAge() + num);
        });

        context.close();
    }
}


@Data
class Student implements Serializable {
    private int age = 10;

}
