package com.kim.utils;

public class ByteUtil {

    /**
     * int转换为小端byte[]（高位放在高地址中）
     */
    public static byte[] int2BytesLE(int value) {
        byte[] rst = new byte[4];
        // int 最后一个字节
        rst[0] = (byte) (value & 0xFF);
        // int 倒数第二个字节
        rst[1] = (byte) ((value & 0xFF00) >> 8);
        // int 倒数第三个字节
        rst[2] = (byte) ((value & 0xFF0000) >> 16);
        // int 倒数第四个字节（第一个字节）
        rst[3] = (byte) ((value & 0xFF000000) >> 24);
        return rst;
    }

    /**
     * byte转换hex函数
     */
    public static String byteToHex(byte[] byteArray) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < byteArray.length; i++) {
            if (Integer.toHexString(0xFF & byteArray[i]).length() == 1) {
                stringBuffer.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
            } else {
                stringBuffer.append(Integer.toHexString(0xFF & byteArray[i]));
            }
            stringBuffer.append(" ");
        }
        return stringBuffer.toString();
    }

    public static String byte2BinaryString(byte nByte) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 7; i >= 0; i--) {
            int j = (int) nByte & (int) (Math.pow(2, (double) i));
            if (j > 0) {
                stringBuilder.append("1");
            } else {
                stringBuilder.append("0");
            }
        }
        return stringBuilder.toString();
    }

    /**
     * 大端转小端
     */
    public static byte[] bytes2byteLE(byte[] input) {
        int length = input.length;
        byte[] bytes = new byte[length];
        for (int i = 0; i < length; i++) {
            bytes[i] = input[length - 1 - i];
        }
        return bytes;
    }

    /**
     * long 小端模式 字节数组
     */
    public static byte[] long2bytesLE(long num) {
        return bytes2byteLE(long2bytes(num));
    }

    /**
     * long转字节数组
     */
    public static byte[] long2bytes(long num) {
        byte[] bytes = new byte[8];
        for (int i = 0; i < 8; i++) {
            bytes[i] = (byte) (num >>> (56 - (i * 8)));
        }
        return bytes;
    }

}
