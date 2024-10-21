package com.kim.algorithm.dynamicprogramming;

import java.util.ArrayList;
import java.util.List;

public class InvestmentProblem {

    // 方法计算最大收益并输出投资方案
    public static int knapsack(int[] profits, int[] costs, int budget) {
        int n = profits.length;
        int[][] dp = new int[n + 1][budget + 1];

        // 填充动态规划表
        for (int i = 1; i <= n; i++) {
            for (int w = 0; w <= budget; w++) {
                if (costs[i - 1] <= w) {
                    dp[i][w] = Math.max(dp[i - 1][w], dp[i - 1][w - costs[i - 1]] + profits[i - 1]);
                } else {
                    dp[i][w] = dp[i - 1][w]; // 不投资
                }
            }
        }

        // 输出投资方案
        List<Integer> investments = new ArrayList<>();
        int w = budget;
        for (int i = n; i > 0; i--) {
            if (dp[i][w] != dp[i - 1][w]) {
                investments.add(i - 1); // 记录选择的投资项
                w -= costs[i - 1]; // 更新剩余预算
            }
        }

        System.out.print("Invest in projects: ");
        for (int i = investments.size() - 1; i >= 0; i--) {
            System.out.print("Project " + (investments.get(i) + 1) + " ");
        }
        System.out.println();

        return dp[n][budget]; // 返回最大收益
    }

    // 主程序
    public static void main(String[] args) {
        int[] profits = {60, 100, 120}; // 投资收益
        int[] costs = {10, 20, 30};      // 投资成本
        int budget = 50;                  // 总预算

        int maxProfit = knapsack(profits, costs, budget);
        System.out.println("Maximum profit: " + maxProfit);
    }
}


