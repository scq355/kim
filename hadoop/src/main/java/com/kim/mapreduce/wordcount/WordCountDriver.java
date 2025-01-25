package com.kim.mapreduce.wordcount;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapreduce.Job;

import java.io.IOException;

public class WordCountDriver {
    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {
        Configuration configuration = new Configuration();

        JobConf jobConf = new JobConf(configuration);
        FileInputFormat.setInputPaths(jobConf, "input.txt");
        FileOutputFormat.setOutputPath(jobConf, new Path("output"));

        Job job = new Job(jobConf, "wordcount");
        job.setMapperClass(WordCountMapper.class);
        job.setReducerClass(WordCountReducer.class);
        job.setJarByClass(WordCountDriver.class);

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);

        boolean result = job.waitForCompletion(true);
        System.exit(result ? 0 : 1);
    }
}
