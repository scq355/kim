package com.kim.mapreduce.compare;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

@ToString
@NoArgsConstructor
@Data
public class FlowBeanCompare implements WritableComparable<FlowBeanCompare> {

    private long upFlow;
    private long downFlow;
    private long sumFlow;

    @Override
    public int compareTo(FlowBeanCompare o) {
        if(o.sumFlow - this.sumFlow > 0) {
            return 1;
        } else if(o.sumFlow - this.sumFlow < 0) {
            return -1;
        }
        return 0;
    }


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
