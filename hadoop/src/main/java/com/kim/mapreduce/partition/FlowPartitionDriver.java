package com.kim.mapreduce.partition;

import com.kim.mapreduce.writable.FlowBean;
import com.kim.mapreduce.writable.FlowDriver;
import com.kim.mapreduce.writable.FlowMapper;
import com.kim.mapreduce.writable.FlowReducer;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapreduce.Job;

import java.io.IOException;

public class FlowPartitionDriver {
    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {
        Configuration configuration = new Configuration();

        JobConf jobConf = new JobConf(configuration);
        FileInputFormat.setInputPaths(jobConf, "input_flow");
        FileOutputFormat.setOutputPath(jobConf, new Path("output_flow"));

        Job job = new Job(jobConf, "flow_bean");
        job.setMapperClass(FlowMapper.class);
        job.setReducerClass(FlowReducer.class);
        job.setJarByClass(FlowDriver.class);

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(FlowBean.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(FlowBean.class);

        job.setPartitionerClass(ProvincePartitioner.class);
        job.setNumReduceTasks(5);

        boolean result = job.waitForCompletion(true);
        System.exit(result ? 0 : 1);
    }
}
