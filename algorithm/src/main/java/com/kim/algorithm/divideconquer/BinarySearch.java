package com.kim.algorithm.divideconquer;

public class BinarySearch {

    // 二分查找方法
    public static int binarySearch(int[] arr, int target) {
        return binarySearchHelper(arr, target, 0, arr.length - 1);
    }

    // 分治算法的递归方法
    private static int binarySearchHelper(int[] arr, int target, int left, int right) {
        if (left > right) {
            return -1; // 未找到目标值
        }

        int mid = left + (right - left) / 2; // 防止溢出

        if (arr[mid] == target) {
            return mid; // 找到目标值，返回索引
        } else if (arr[mid] > target) {
            return binarySearchHelper(arr, target, left, mid - 1); // 在左半部分查找
        } else {
            return binarySearchHelper(arr, target, mid + 1, right); // 在右半部分查找
        }
    }

    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int target = 5;
        int result = binarySearch(arr, target);

        if (result != -1) {
            System.out.println("目标值 " + target + " 的索引是: " + result);
        } else {
            System.out.println("未找到目标值 " + target);
        }
    }
}

