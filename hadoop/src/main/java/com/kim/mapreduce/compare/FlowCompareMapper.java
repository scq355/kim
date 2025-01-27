package com.kim.mapreduce.compare;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class FlowCompareMapper extends Mapper<LongWritable, Text, FlowBeanCompare, Text> {

    private FlowBeanCompare outK = new FlowBeanCompare();
    private Text outV = new Text();

    @Override
    protected void map(LongWritable key, Text value,
                       Mapper<LongWritable, Text, FlowBeanCompare, Text>.Context context) throws IOException, InterruptedException {
        String line = value.toString();

        String[] split = line.split("\t");

        outK.setUpFlow(Long.parseLong(split[1]));
        outK.setDownFlow(Long.parseLong(split[2]));
        outK.setSumFlow(Long.parseLong(split[1] + split[2]));
        outV.set(split[0]);

        context.write(outK, outV);
    }
}
