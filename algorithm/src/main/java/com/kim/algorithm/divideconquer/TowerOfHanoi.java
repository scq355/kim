package com.kim.algorithm.divideconquer;

public class TowerOfHanoi {

    // 主方法，接收盘子的数量、源柱、目标柱和辅助柱
    public static void solveHanoi(int n, char source, char target, char auxiliary) {
        if (n == 1) {
            System.out.println("Move disk 1 from " + source + " to " + target);
            return;
        }

        // 第一步：将 n-1 个盘子从源柱移动到辅助柱
        solveHanoi(n - 1, source, auxiliary, target);

        // 第二步：将第 n 个盘子从源柱移动到目标柱
        System.out.println("Move disk " + n + " from " + source + " to " + target);

        // 第三步：将 n-1 个盘子从辅助柱移动到目标柱
        solveHanoi(n - 1, auxiliary, target, source);
    }

    // 主程序
    public static void main(String[] args) {
        int n = 3; // 盘子的数量
        System.out.println("The moves involved in the Tower of Hanoi are:");
        solveHanoi(n, 'A', 'C', 'B'); // A, B, C 分别表示源柱、目标柱和辅助柱
    }
}

