package com.kim.algorithm.divideconquer;

public class Power {

    // 计算 a 的 n 次幂
    public static double power(double a, int n) {
        if (n == 0) {
            return 1; // 任何数的 0 次幂都为 1
        }
        if (n < 0) {
            a = 1 / a; // 处理负指数
            n = -n;
        }
        return powerHelper(a, n);
    }

    // 分治算法的递归方法
    private static double powerHelper(double a, int n) {
        if (n == 0) {
            return 1; // 基础情况
        }
        double half = powerHelper(a, n / 2);
        if (n % 2 == 0) {
            return half * half; // 偶数次幂
        } else {
            return half * half * a; // 奇数次幂
        }
    }

    public static void main(String[] args) {
        double a = 2.0;
        int n = 10;
        double result = power(a, n);
        System.out.println(a + " 的 " + n + " 次幂是: " + result);
    }
}

