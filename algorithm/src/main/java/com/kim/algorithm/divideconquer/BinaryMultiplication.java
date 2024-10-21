package com.kim.algorithm.divideconquer;

public class BinaryMultiplication {

    // 主方法，接受两个二进制字符串
    public static String multiply(String X, String Y) {
        // 确保输入为相同长度
        int n = Math.max(X.length(), Y.length());
        // 将长度补齐为 2 的幂次
        n = (int) Math.pow(2, Math.ceil(Math.log(n) / Math.log(2)));

        // 左侧补零以匹配长度
        X = padLeft(X, n);
        Y = padLeft(Y, n);

        return multiplyHelper(X, Y);
    }

    // 分治算法实现
    private static String multiplyHelper(String X, String Y) {
        int n = X.length();

        // 基础情况
        if (n == 1) {
            return String.valueOf(Integer.parseInt(X, 2) * Integer.parseInt(Y, 2));
        }

        // 分割
        int half = n / 2;
        String X1 = X.substring(0, half); // 高位
        String X0 = X.substring(half);     // 低位
        String Y1 = Y.substring(0, half);  // 高位
        String Y0 = Y.substring(half);      // 低位

        // 递归计算
        String Z0 = multiplyHelper(X0, Y0); // X0 * Y0
        String Z1 = multiplyHelper(add(X0, X1), add(Y0, Y1)); // (X0 + X1) * (Y0 + Y1)
        String Z2 = multiplyHelper(X1, Y1); // X1 * Y1

        // 计算结果
        String result = add(addZeros(Z2, 2 * half), add(addZeros(subtract(Z1, add(Z0, Z2)), 1), Z0));
        return result;
    }

    // 字符串左侧补零
    private static String padLeft(String str, int length) {
        StringBuilder sb = new StringBuilder(str);
        while (sb.length() < length) {
            sb.insert(0, '0');
        }
        return sb.toString();
    }

    // 二进制字符串相加
    private static String add(String a, String b) {
        return Integer.toBinaryString(Integer.parseInt(a, 2) + Integer.parseInt(b, 2));
    }

    // 字符串添加零
    private static String addZeros(String str, int zeros) {
        StringBuilder sb = new StringBuilder(str);
        for (int i = 0; i < zeros; i++) {
            sb.append('0');
        }
        return sb.toString();
    }

    // 二进制字符串相减
    private static String subtract(String a, String b) {
        int aValue = Integer.parseInt(a, 2);
        int bValue = Integer.parseInt(b, 2);
        return Integer.toBinaryString(Math.max(0, aValue - bValue)); // 确保不会出现负值
    }

    public static void main(String[] args) {
        String X = "1101"; // 二进制数 X
        String Y = "1011"; // 二进制数 Y

        String result = multiply(X, Y);
        System.out.println("Result of " + X + " * " + Y + " = " + result);
    }
}



