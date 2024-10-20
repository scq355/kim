package com.kim.datastructure.string;

/**
 * 朴素模式匹配
 */
public class NaivePatternSearch {
    public static void search(String text, String pattern) {
        int n = text.length();
        int m = pattern.length();

        for (int i = 0; i <= n - m; i++) {
            int j;
            for (j = 0; j < m; j++) {
                if (text.charAt(i + j) != pattern.charAt(j)) {
                    break; // 匹配失败
                }
            }
            if (j == m) {
                System.out.println("模式找到，起始索引: " + i);
            }
        }
    }

    public static void main(String[] args) {
        String text = "ABABDABACDABABCABAB";
        String pattern = "ABABCABAB";
        search(text, pattern); // 输出: 模式找到，起始索引: 10
    }
}

