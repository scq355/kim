package com.kim.spark.req;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Tuple2;

public class HotCategoryTop10 {

    public static void main(String[] args) {
        SparkConf conf = new SparkConf();
        conf.setAppName("hotcategory");
        conf.setMaster("local[*]");
        JavaSparkContext context = new JavaSparkContext(conf);

        JavaRDD<String> rdd = context.textFile("data/user_visit_action.txt");


        JavaRDD<String> filterRdd = rdd.filter(line -> {
            String[] ss = line.split("\t");
            return "\\N".equals(ss[5]);
        });
        // 点击数量统计
        JavaRDD<String> clickDataRdd = filterRdd.filter(line -> {
            String[] ss = line.split("\t");
            return !"-1".equals(ss[6]);
        });

        JavaPairRDD<String, Integer> clickCountRdd = clickDataRdd.mapToPair(line -> {
                    String[] ss = line.split("\t");
                    return new Tuple2<>(ss[6], 1);
                })
                .reduceByKey(Integer::sum);

        System.out.println(clickCountRdd.take(10));

        context.close();
    }
}
