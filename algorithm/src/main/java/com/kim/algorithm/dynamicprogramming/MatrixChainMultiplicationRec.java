package com.kim.algorithm.dynamicprogramming;

import java.util.Arrays;

public class MatrixChainMultiplicationRec {

    // 方法计算矩阵链乘法的最小乘法次数
    public static int matrixChainOrder(int[] p) {
        int n = p.length - 1; // 矩阵的个数
        int[][] memo = new int[n][n]; // 存储最小乘法次数
        int[][] split = new int[n][n]; // 存储分割点

        // 初始化 memo 数组为 -1
        for (int[] row : memo) {
            Arrays.fill(row, -1);
        }

        // 计算最小乘法次数
        int minCost = matrixChainRecursive(memo, split, p, 0, n - 1);

        // 打印最优方案
        System.out.print("Optimal Parenthesization: ");
        printOptimalParenthesis(split, 0, n - 1);
        System.out.println();

        return minCost; // 返回最小乘法次数
    }

    // 递归方法计算最小乘法次数
    private static int matrixChainRecursive(int[][] memo, int[][] split, int[] p, int i, int j) {
        if (i == j) {
            return 0; // 只有一个矩阵时不需要乘法
        }

        if (memo[i][j] != -1) {
            return memo[i][j]; // 返回已经计算过的结果
        }

        memo[i][j] = Integer.MAX_VALUE;

        // 计算 m[i][j]
        for (int k = i; k < j; k++) {
            int q = matrixChainRecursive(memo, split, p, i, k) +
                    matrixChainRecursive(memo, split, p, k + 1, j) +
                    p[i] * p[k + 1] * p[j + 1];

            if (q < memo[i][j]) {
                memo[i][j] = q;
                split[i][j] = k; // 记录最优分割点
            }
        }

        return memo[i][j]; // 返回最小乘法次数
    }

    // 打印最优括号
    private static void printOptimalParenthesis(int[][] split, int i, int j) {
        if (i == j) {
            System.out.print("A" + (i + 1)); // 输出矩阵名
        } else {
            System.out.print("(");
            printOptimalParenthesis(split, i, split[i][j]);
            printOptimalParenthesis(split, split[i][j] + 1, j);
            System.out.print(")");
        }
    }

    // 主程序
    public static void main(String[] args) {
        // 矩阵链的维度数组（A1: 10x20, A2: 20x30, A3: 30x40, A4: 40x30）
        int[] p = {10, 20, 30, 40, 30};

        int minCost = matrixChainOrder(p);
        System.out.println("Minimum number of multiplications is: " + minCost);
    }
}

