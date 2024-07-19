package com.kim.spark.rdd.operate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Serializable;
import scala.Tuple2;

import java.util.Arrays;

public class Spark13_Operate_Transform_KV_SortBykey_1 {
    public static void main(String[] args) throws Exception {
        SparkConf conf = new SparkConf();
        conf.setMaster("local[*]");
        conf.setAppName("spark");

        JavaSparkContext context = new JavaSparkContext(conf);

        context.parallelizePairs(
                        Arrays.asList(
                                new Tuple2<>(User.builder().age(16).salary(1200).build(), 10),
                                new Tuple2<>(User.builder().age(18).salary(2200).build(), 3),
                                new Tuple2<>(User.builder().age(17).salary(2100).build(), 4),
                                new Tuple2<>(User.builder().age(21).salary(3100).build(), 2)
                        )
                )
                .sortByKey()
                .collect()
                .forEach(System.out::println);
        context.close();
    }
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class User implements Serializable, Comparable<User> {
    private int age = 0;
    private int salary = 0;

    @Override
    public String toString() {
        return "User{" + "age=" + age + ", salary=" + salary + '}';
    }

    @Override
    public int compareTo(User u) {
        if (this.age == u.age) {
            return u.salary - this.salary;
        }
        return this.age - u.age;
    }
}
