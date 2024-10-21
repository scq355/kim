package com.kim.algorithm.dynamicprogramming;

public class LongestCommonSubsequence {

    // 方法计算最长公共子序列的长度
    public static int lcs(String str1, String str2) {
        int m = str1.length();
        int n = str2.length();
        int[][] dp = new int[m + 1][n + 1];

        // 填充动态规划表
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (str1.charAt(i - 1) == str2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1; // 如果字符相同
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]); // 否则取最大值
                }
            }
        }

        return dp[m][n]; // 返回最长公共子序列的长度
    }

    // 方法输出最长公共子序列
    public static String getLCS(String str1, String str2) {
        int m = str1.length();
        int n = str2.length();
        int[][] dp = new int[m + 1][n + 1];

        // 填充动态规划表
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (str1.charAt(i - 1) == str2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1; // 如果字符相同
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]); // 否则取最大值
                }
            }
        }

        // 反向构建 LCS 字符串
        StringBuilder lcs = new StringBuilder();
        int i = m, j = n;
        while (i > 0 && j > 0) {
            if (str1.charAt(i - 1) == str2.charAt(j - 1)) {
                lcs.append(str1.charAt(i - 1)); // 添加字符
                i--;
                j--;
            } else if (dp[i - 1][j] > dp[i][j - 1]) {
                i--; // 向上移动
            } else {
                j--; // 向左移动
            }
        }

        return lcs.reverse().toString(); // 返回反转后的 LCS 字符串
    }

    // 主程序
    public static void main(String[] args) {
        String str1 = "AGGTAB";
        String str2 = "GXTXAYB";

        int length = lcs(str1, str2);
        String lcsString = getLCS(str1, str2);

        System.out.println("Length of Longest Common Subsequence: " + length);
        System.out.println("Longest Common Subsequence: " + lcsString);
    }
}

