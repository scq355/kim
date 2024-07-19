package com.kim.sychronized;

import org.openjdk.jol.info.ClassLayout;
import com.kim.utils.ByteUtil;
import com.kim.utils.Print;

public class ObjectLock {
    private Integer amount = 0;

    public void increase() {
        synchronized (this) {
            amount++;
        }
    }

    /**
     * 十六进制小端模式hashCode
     */
    public String hexHash() {
        return ByteUtil.byteToHex(ByteUtil.int2BytesLE(this.hashCode()));
    }

    /**
     * 二进制小端模式hashCode
     */
    public String binaryHash() {
        byte[] hashCodeLE = ByteUtil.int2BytesLE(this.hashCode());
        StringBuffer stringBuffer = new StringBuffer();
        for (byte b : hashCodeLE) {
            stringBuffer.append(ByteUtil.byte2BinaryString(b));
            stringBuffer.append(" ");
        }
        return stringBuffer.toString();
    }

    /**
     * 十六进制小端模式threadId
     */
    public String hexThreadId() {
        long threadId = Thread.currentThread().getId();
        byte[] threadLE = ByteUtil.long2bytesLE(threadId);
        return ByteUtil.byteToHex(threadLE);
    }

    /**
     * 二进制小端模式threadID
     */
    public String binaryThreadId() {
        long threadId = Thread.currentThread().getId();
        byte[] threadIdLE = ByteUtil.long2bytesLE(threadId);
        StringBuffer buffer = new StringBuffer();
        for (byte b : threadIdLE) {
            buffer.append(ByteUtil.byte2BinaryString(b));
            buffer.append(" ");
        }
        return buffer.toString();
    }

    public void printSelf() {
        // 十六进制小端模式hashCode
        Print.fo("lock hexHash=" + hexHash());
        // 二进制小端模式hashCode
        Print.fo("lock binaryHash=" + binaryHash());

        // 获取this对象布局
        String printable = ClassLayout.parseInstance(this).toPrintable();
        Print.fo("lock=" + printable);
    }

    public void printObjectStruct() {
        String printable = ClassLayout.parseInstance(this).toPrintable();
        Print.fo("lock=" + printable);
    }
}
