package com.kim.spark.dep;

import org.apache.spark.Partitioner;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Tuple2;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Spark08_Partition_1 {
    public static void main(String[] args) throws Exception {
        SparkConf conf = new SparkConf();
        conf.setMaster("local[*]");
        conf.setAppName("spark");

        JavaSparkContext context = new JavaSparkContext(conf);
        // 推荐hdfs，也可以本地
        context.setCheckpointDir("checkpoint/");

        List<Tuple2<String, Integer>> datas = Arrays.asList(
                new Tuple2<>("a", 1),
                new Tuple2<>("b", 2),
                new Tuple2<>("a", 3),
                new Tuple2<>("c", 4)
        );

        JavaRDD<Tuple2<String, Integer>> rdd = context.parallelize(datas, 2);
        JavaPairRDD<String, Integer> mapRdd = rdd.mapToPair(kv -> {
            System.out.println("******************************");
            return kv;
        });

        // 数据分区规则：
        JavaPairRDD<String, Integer> reduceRdd = mapRdd.reduceByKey(new MyPartitioner(3), Integer::sum);
        JavaPairRDD<String, Integer> reduceRdd1 = reduceRdd.reduceByKey(new MyPartitioner(3), Integer::sum);
        reduceRdd1.collect();

        Thread.sleep(10000000L);

        context.close();
    }
}

class MyPartitioner extends Partitioner {

    private final int numPartitions;

    public MyPartitioner(int numPartitions) {
        this.numPartitions = numPartitions;
    }


    @Override
    public int numPartitions() {
        return this.numPartitions;
    }

    @Override
    public int getPartition(Object key) {
        if ("a".equals(key)) {
            return 0;
        } else if ("b".equals(key)) {
            return 1;
        }
        return 2;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MyPartitioner that = (MyPartitioner) o;
        return numPartitions == that.numPartitions;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(numPartitions);
    }
}
