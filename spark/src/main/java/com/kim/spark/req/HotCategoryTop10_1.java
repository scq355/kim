package com.kim.spark.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Serializable;
import scala.Tuple2;

public class HotCategoryTop10_1 {

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

        JavaRDD<HotCategory> mapRdd = filterRdd.map(line -> {
            String[] ss = line.split("\t");
            if (!"-1".equals(ss[6])) {
                // 点击
                return new HotCategory(ss[6], 1L, 0L, 0L);
            } else if (!"\\N".equals(ss[8])) {
                // 下单
                return new HotCategory(ss[8], 0L, 1L, 0L);
            } else {
                // 支付
                return new HotCategory(ss[10], 0L, 0L, 1L);
            }
        });

        JavaPairRDD<String, HotCategory> kvRdd = mapRdd.mapToPair(
                obj -> new Tuple2<>(obj.getId(), obj)
        );

        JavaPairRDD<String, HotCategory> objCountRdd = kvRdd.reduceByKey((obj1, obj2) -> {
            obj1.setClickCount(obj1.getClickCount() + obj2.getClickCount());
            obj1.setOrderCount(obj1.getOrderCount() + obj2.getOrderCount());
            obj1.setPayCount(obj1.getPayCount() + obj2.getPayCount());
            return obj1;
        });

        objCountRdd.take(10).forEach(System.out::println);

        context.close();
    }
}

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
class HotCategory implements Serializable, Comparable<HotCategory> {
    private String id;
    private Long clickCount;
    private Long orderCount;
    private Long payCount;

    @Override
    public int compareTo(HotCategory o) {
        if (this.getClickCount() == o.getClickCount()) {
            if (this.getOrderCount() == o.getOrderCount()) {
                return Math.toIntExact(o.getPayCount() - this.payCount);
            }
            return Math.toIntExact(o.orderCount - this.getOrderCount());
        }
        return Math.toIntExact(o.getClickCount() - this.clickCount);
    }
}
