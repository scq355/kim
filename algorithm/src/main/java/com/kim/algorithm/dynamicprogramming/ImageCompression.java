package com.kim.algorithm.dynamicprogramming;

import java.util.Arrays;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ImageCompression {

    // 计算均方误差（MSE）
    private static double calculateMSE(int[] array, int start, int end) {
        int sum = 0;
        int count = end - start + 1;

        for (int i = start; i <= end; i++) {
            sum += array[i];
        }

        double mean = (double) sum / count;
        double mse = 0;

        for (int i = start; i <= end; i++) {
            mse += Math.pow(array[i] - mean, 2);
        }

        return mse; // 返回均方误差
    }

    // 方法计算最优划分
    public static double minPartitions(int[] array, int maxPartitions, List<Integer> cuts) {
        int n = array.length;
        double[][] dp = new double[n + 1][maxPartitions + 1];
        int[][] split = new int[n + 1][maxPartitions + 1];

        // 初始化 dp 数组
        for (double[] row : dp) {
            Arrays.fill(row, Double.MAX_VALUE);
        }
        dp[0][0] = 0;

        // 填充动态规划表
        for (int k = 1; k <= maxPartitions; k++) {
            for (int i = 1; i <= n; i++) {
                for (int j = 0; j < i; j++) {
                    double mse = calculateMSE(array, j, i - 1);
                    if (dp[j][k - 1] + mse < dp[i][k]) {
                        dp[i][k] = dp[j][k - 1] + mse;
                        split[i][k] = j; // 记录划分点
                    }
                }
            }
        }

        // 追踪切割点以获取最优划分
        int remainingPartitions = maxPartitions;
        int currentIndex = n;
        while (remainingPartitions > 0) {
            cuts.add(split[currentIndex][remainingPartitions]);
            currentIndex = split[currentIndex][remainingPartitions];
            remainingPartitions--;
        }

        // 返回最小划分成本
        return dp[n][maxPartitions];
    }

    // 主程序
    public static void main(String[] args) {
        int[] grayLevels = {10, 10, 20, 30, 30, 40, 50, 50, 50}; // 示例灰度序列
        int maxPartitions = 3; // 最大划分数

        List<Integer> cuts = new ArrayList<>();
        double minCost = minPartitions(grayLevels, maxPartitions, cuts);

        System.out.println("Minimum compression cost: " + minCost);
        System.out.print("Optimal cuts at indices: ");
        for (int i = cuts.size() - 1; i >= 0; i--) {
            System.out.print(cuts.get(i) + " ");
        }
        System.out.println();
    }
}

