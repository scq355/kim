package com.kim.mapreduce.outputformat;

import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.RecordWriter;
import org.apache.hadoop.mapreduce.TaskAttemptContext;

import java.io.IOException;

@Slf4j
public class LogRecordWriter extends RecordWriter<Text, NullWritable> {

    private FSDataOutputStream out;
    private FSDataOutputStream other;

    public LogRecordWriter(TaskAttemptContext context) {
        try {
            FileSystem fileSystem = FileSystem.get(context.getConfiguration());
            out = fileSystem.create(new Path("/data/log/a.log"));
            other = fileSystem.create(new Path("/data/log/o.log"));
        } catch (IOException e) {
            log.error("LogRecordWriter exception", e);
        }
    }

    @Override
    public void write(Text key, NullWritable value) throws IOException, InterruptedException {
        String log = key.toString();
        if (log.contains("atguigu")) {
            out.writeBytes(log + "\n");
        } else {
            other.writeBytes(log + "\n");
        }
    }

    @Override
    public void close(TaskAttemptContext taskAttemptContext) throws IOException, InterruptedException {
        IOUtils.closeStream(out);
        IOUtils.closeStream(other);
    }
}
