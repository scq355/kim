package com.kim.mapreduce.compare;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapreduce.Job;

import java.io.IOException;

public class FlowCompareDriver {

    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {
        Configuration configuration = new Configuration();
        Job job = Job.getInstance(configuration);

        job.setJarByClass(FlowCompareDriver.class);

        job.setMapperClass(FlowCompareMapper.class);
        job.setReducerClass(FlowCompareReducer.class);

        job.setMapOutputKeyClass(FlowCompareMapper.class);
        job.setMapOutputValueClass(Text.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(FlowBeanCompare.class);

        JobConf jobConf = new JobConf(configuration);

        FileOutputFormat.setOutputPath(jobConf, new Path("input_flow_compare"));
        FileInputFormat.setInputPaths(jobConf, "output_flow_compare");

        boolean result = job.waitForCompletion(true);

        System.exit(result ? 0 : 1);
    }
}
