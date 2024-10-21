package com.kim.algorithm.divideconquer;

import java.util.Arrays;

public class QuickSort {

    // 主方法，接收数组并调用快速排序
    public static void quickSort(int[] arr) {
        quickSortRec(arr, 0, arr.length - 1);
    }

    // 递归快速排序方法
    private static void quickSortRec(int[] arr, int low, int high) {
        if (low < high) {
            // 获取划分点
            int pivotIndex = partition(arr, low, high);
            // 递归排序低于划分点和高于划分点的部分
            quickSortRec(arr, low, pivotIndex - 1);
            quickSortRec(arr, pivotIndex + 1, high);
        }
    }

    // 划分方法
    private static int partition(int[] arr, int low, int high) {
        int pivot = arr[high]; // 选择最后一个元素作为枢轴
        int i = low - 1; // 小于枢轴的元素的索引

        for (int j = low; j < high; j++) {
            if (arr[j] < pivot) {
                i++;
                swap(arr, i, j); // 交换元素
            }
        }
        swap(arr, i + 1, high); // 将枢轴放到正确位置
        return i + 1; // 返回枢轴索引
    }

    // 交换数组中的两个元素
    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    // 主程序
    public static void main(String[] args) {
        int[] arr = {3, 6, 8, 10, 1, 2, 1};
        System.out.println("Original array: " + Arrays.toString(arr));

        quickSort(arr);

        System.out.println("Sorted array: " + Arrays.toString(arr));
    }
}

