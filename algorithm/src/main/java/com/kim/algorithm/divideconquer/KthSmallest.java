package com.kim.algorithm.divideconquer;

import java.util.Random;

public class KthSmallest {

    private static Random random = new Random();

    // 主方法，接收数组和 k 值
    public static int findKthSmallest(int[] arr, int k) {
        if (k < 1 || k > arr.length) {
            throw new IllegalArgumentException("k must be between 1 and the length of the array.");
        }
        return quickSelect(arr, 0, arr.length - 1, k - 1);
    }

    // 快速选择算法
    private static int quickSelect(int[] arr, int low, int high, int k) {
        if (low == high) {
            return arr[low]; // 只有一个元素
        }

        int pivotIndex = random.nextInt(high - low + 1) + low; // 随机选择枢轴
        pivotIndex = partition(arr, low, high, pivotIndex);

        // 根据枢轴位置与 k 比较
        if (k == pivotIndex) {
            return arr[k]; // 找到第 k 小元素
        } else if (k < pivotIndex) {
            return quickSelect(arr, low, pivotIndex - 1, k); // 在左半部分继续查找
        } else {
            return quickSelect(arr, pivotIndex + 1, high, k); // 在右半部分继续查找
        }
    }

    // 划分方法
    private static int partition(int[] arr, int low, int high, int pivotIndex) {
        int pivotValue = arr[pivotIndex];
        // 移动枢轴到末尾
        swap(arr, pivotIndex, high);
        int storeIndex = low;

        for (int i = low; i < high; i++) {
            if (arr[i] < pivotValue) {
                swap(arr, storeIndex, i);
                storeIndex++;
            }
        }
        // 将枢轴移回到它的最终位置
        swap(arr, storeIndex, high);
        return storeIndex;
    }

    // 交换数组中的两个元素
    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    // 主程序
    public static void main(String[] args) {
        int[] arr = {3, 5, 1, 4, 2, 6};
        int k = 3; // 查找第 k 小元素
        int kthSmallest = findKthSmallest(arr, k);
        System.out.println("The " + k + "th smallest element is: " + kthSmallest);
    }
}
