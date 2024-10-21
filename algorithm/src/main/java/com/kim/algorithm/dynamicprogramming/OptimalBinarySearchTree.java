package com.kim.algorithm.dynamicprogramming;

public class OptimalBinarySearchTree {

    // 计算最优二叉搜索树的最小查找成本
    public static double optimalBST(int[] keys, double[] freq) {
        int n = keys.length;
        double[][] cost = new double[n + 1][n + 1];

        // 初始化成本为 0
        for (int i = 0; i <= n; i++) {
            cost[i][i] = 0;
        }

        // 计算每个长度的子树
        for (int length = 1; length <= n; length++) {
            for (int i = 0; i <= n - length; i++) {
                int j = i + length;
                cost[i][j] = Double.MAX_VALUE;

                // 选择每个可能的根节点
                for (int r = i; r < j; r++) {
                    double totalFreq = 0;
                    for (int k = i; k < j; k++) {
                        totalFreq += freq[k]; // 计算频率和
                    }
                    double currentCost = cost[i][r] + cost[r + 1][j] + totalFreq;
                    cost[i][j] = Math.min(cost[i][j], currentCost);
                }
            }
        }

        return cost[0][n]; // 返回最优成本
    }

    // 主程序
    public static void main(String[] args) {
        int[] keys = {10, 20, 30}; // 键
        double[] freq = {0.4, 0.3, 0.3}; // 频率

        double minCost = optimalBST(keys, freq);
        System.out.println("Minimum cost of optimal BST: " + minCost);
    }
}

