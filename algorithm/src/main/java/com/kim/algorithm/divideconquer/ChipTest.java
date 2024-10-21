package com.kim.algorithm.divideconquer;

public class ChipTest {

    // 芯片的状态（1 表示正常，0 表示故障）
    private int[] chipStatus;

    public ChipTest(int[] status) {
        this.chipStatus = status;
    }

    // 主测试方法
    public int testChip() {
        return testChipHelper(0, chipStatus.length - 1);
    }

    // 分治算法的递归方法
    private int testChipHelper(int left, int right) {
        if (left == right) {
            // 只有一个单元
            return (chipStatus[left] == 0) ? left : -1; // 返回故障单元的索引，或 -1 表示正常
        }

        int mid = left + (right - left) / 2; // 防止溢出

        // 递归检查左半部分和右半部分
        int leftFault = testChipHelper(left, mid);
        if (leftFault != -1) {
            return leftFault; // 如果左半部分发现故障，返回故障单元索引
        }
        return testChipHelper(mid + 1, right); // 检查右半部分
    }

    public static void main(String[] args) {
        int[] chipStatus = {1, 1, 0, 1, 1, 0, 1}; // 0 表示故障单元
        ChipTest chipTest = new ChipTest(chipStatus);
        int faultIndex = chipTest.testChip();

        if (faultIndex != -1) {
            System.out.println("故障单元的索引是: " + faultIndex);
        } else {
            System.out.println("没有发现故障单元。");
        }
    }
}

