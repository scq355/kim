package com.kim.datastructure.string;

public class KMP {
    private int[] lps; // 存储最长前缀后缀数组

    // 计算最长前缀后缀数组
    private void computeLPSArray(String pattern) {
        int m = pattern.length();
        lps = new int[m];
        int len = 0; // 长度
        lps[0] = 0;  // 第一个字符的 lps 值是 0

        for (int i = 1; i < m; i++) {
            while (len > 0 && pattern.charAt(i) != pattern.charAt(len)) {
                len = lps[len - 1]; // 回溯
            }
            if (pattern.charAt(i) == pattern.charAt(len)) {
                len++;
                lps[i] = len;
            } else {
                lps[i] = 0;
            }
        }
    }

    // KMP 模式匹配
    public void search(String text, String pattern) {
        int n = text.length();
        int m = pattern.length();
        computeLPSArray(pattern);

        int i = 0; // text 的索引
        int j = 0; // pattern 的索引
        while (i < n) {
            if (pattern.charAt(j) == text.charAt(i)) {
                i++;
                j++;
            }
            if (j == m) {
                System.out.println("模式找到，起始索引: " + (i - j));
                j = lps[j - 1]; // 回溯
            } else if (i < n && pattern.charAt(j) != text.charAt(i)) {
                if (j != 0) {
                    j = lps[j - 1]; // 使用 lps 数组回溯
                } else {
                    i++;
                }
            }
        }
    }

    public static void main(String[] args) {
        KMP kmp = new KMP();
        String text = "ABABDABACDABABCABAB";
        String pattern = "ABABCABAB";
        kmp.search(text, pattern); // 输出: 模式找到，起始索引: 10
    }
}

