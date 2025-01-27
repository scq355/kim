package com.kim.mapreduce.compare;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class FlowCompareReducer extends Reducer<FlowBeanCompare, Text, Text, FlowBeanCompare> {

    @Override
    protected void reduce(FlowBeanCompare key, Iterable<Text> values, Reducer<FlowBeanCompare, Text, Text, FlowBeanCompare>.Context context) throws IOException, InterruptedException {
        for (Text value : values) {
            context.write(value, key);
        }
    }
}
