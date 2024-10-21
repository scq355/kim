package com.kim.algorithm.divideconquer;

public class Fibonacci {

    // 分治递归方法计算 Fibonacci 数列
    public static int fibonacci(int n) {
        if (n <= 1) {
            return n; // 基础情况
        }
        return fibonacci(n - 1) + fibonacci(n - 2); // 分治
    }

    public static void main(String[] args) {
        int n = 10; // 计算第 n 个 Fibonacci 数
        int result = fibonacci(n);
        System.out.println("Fibonacci(" + n + ") = " + result);
    }
}

