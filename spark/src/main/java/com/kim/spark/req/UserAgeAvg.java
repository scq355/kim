package com.kim.spark.req;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

public class UserAgeAvg {

    public static void main(String[] args) {
        SparkConf conf = new SparkConf();
        conf.setAppName("userageavg");
        conf.setMaster("local[*]");
        JavaSparkContext context = new JavaSparkContext(conf);

        JavaRDD<String> dataRdd = context.textFile("data/user.txt");
        JavaRDD<Integer> ageRdd = dataRdd.map(line -> {
            int age = 0;
            String lineData = line.trim();
            String attrsData = lineData.substring(1, lineData.length() - 1);
            String[] attrs = attrsData.split(",");
            for (String attr : attrs) {
                String[] kv = attr.trim().split(":");
                for (int i = 0; i < kv.length; i++) {
                    if ("\"age\"".equals(kv[i].trim())) {
                        age = Integer.parseInt(kv[i + 1]);
                        break;
                    }
                }
            }
            return age;
        });


        System.out.println(ageRdd.reduce(Integer::sum) / ageRdd.count());

        context.close();
    }
}
