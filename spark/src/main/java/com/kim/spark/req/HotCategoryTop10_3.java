package com.kim.spark.req;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Tuple2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class HotCategoryTop10_3 {

    public static void main(String[] args) {
        SparkConf conf = new SparkConf();
        conf.setAppName("hotcategory");
        conf.setMaster("local[*]");
        JavaSparkContext context = new JavaSparkContext(conf);

        // 若当前执行环境为yarn（spark on yarn）,则相对路径为hdfs
        context.textFile("data/user_visit_action.txt")
                .filter(line -> {
                    String[] ss = line.split("\t");
                    return "\\N".equals(ss[5]);
                })
                .flatMap(line -> {
                    String[] ss = line.split("\t");
                    if (!"-1".equals(ss[6])) {
                        // 点击
                        return Collections.singletonList(new HotCategory(ss[6], 1L, 0L, 0L)).iterator();
                    } else if (!"\\N".equals(ss[8])) {
                        // 下单
                        List<HotCategory> data = new ArrayList<>();
                        Arrays.asList(ss[8].split(",")).forEach(id -> data.add(new HotCategory(id, 0L, 1L, 0L)));
                        return data.iterator();
                    } else {
                        // 支付
                        List<HotCategory> data = new ArrayList<>();
                        Arrays.asList(ss[10].split(",")).forEach(id -> data.add(new HotCategory(id, 0L, 0L, 1L)));
                        return data.iterator();
                    }
                })
                .mapToPair(obj -> new Tuple2<>(obj.getId(), obj))
                .reduceByKey((obj1, obj2) -> {
                    obj1.setClickCount(obj1.getClickCount() + obj2.getClickCount());
                    obj1.setOrderCount(obj1.getOrderCount() + obj2.getOrderCount());
                    obj1.setPayCount(obj1.getPayCount() + obj2.getPayCount());
                    return obj1;
                })
                .map(kv -> kv._2)
                .sortBy(obj -> obj, true, 2)
                .take(10)
                .forEach(System.out::println);

        context.close();
    }
}
