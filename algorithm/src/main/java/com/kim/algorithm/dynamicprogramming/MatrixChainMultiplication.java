package com.kim.algorithm.dynamicprogramming;

public class MatrixChainMultiplication {

    // 方法计算矩阵链乘法的最小乘法次数
    public static int matrixChainOrder(int[] p) {
        int n = p.length - 1; // 矩阵的个数
        int[][] m = new int[n][n]; // 存储最小乘法次数的表
        int[][] s = new int[n][n]; // 存储分割点的表

        // l 是链的长度
        for (int l = 2; l <= n; l++) {
            for (int i = 0; i < n - l + 1; i++) {
                int j = i + l - 1;
                m[i][j] = Integer.MAX_VALUE;

                // 计算 m[i][j]
                for (int k = i; k < j; k++) {
                    int q = m[i][k] + m[k + 1][j] + p[i] * p[k + 1] * p[j + 1];
                    if (q < m[i][j]) {
                        m[i][j] = q;
                        s[i][j] = k; // 记录分割点
                    }
                }
            }
        }

        printOptimalParenthesis(s, 0, n - 1); // 打印最优括号
        return m[0][n - 1]; // 返回最小乘法次数
    }

    // 方法打印最优括号
    private static void printOptimalParenthesis(int[][] s, int i, int j) {
        if (i == j) {
            System.out.print("A" + (i + 1)); // 输出矩阵名
        } else {
            System.out.print("(");
            printOptimalParenthesis(s, i, s[i][j]);
            printOptimalParenthesis(s, s[i][j] + 1, j);
            System.out.print(")");
        }
    }

    // 主程序
    public static void main(String[] args) {
        // 矩阵链的维度数组（A1: 10x20, A2: 20x30, A3: 30x40, A4: 40x30）
        int[] p = {10, 20, 30, 40, 30};

        int minCost = matrixChainOrder(p);
        System.out.println("\nMinimum number of multiplications is: " + minCost);
    }
}
