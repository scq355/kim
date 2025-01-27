package com.kim.mapreduce.writable;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

@ToString
@NoArgsConstructor
@Data
public class FlowBean implements Writable {
    private long upFlow;
    private long downFlow;
    private long sumFlow;


    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeLong(upFlow);
        dataOutput.writeLong(downFlow);
        dataOutput.writeLong(sumFlow);
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        this.upFlow = dataInput.readLong();
        this.downFlow = dataInput.readLong();
        this.sumFlow = dataInput.readLong();
    }


}
