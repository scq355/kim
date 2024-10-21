package com.kim.algorithm.divideconquer;

public class SecondLargest {

    // 主方法，接收数组并调用递归查找第二大元素
    public static int findSecondLargest(int[] arr) {
        if (arr.length < 2) {
            throw new IllegalArgumentException("Array must contain at least two elements.");
        }
        return findSecondLargestRec(arr, 0, arr.length - 1)[1];
    }

    // 递归方法
    private static int[] findSecondLargestRec(int[] arr, int low, int high) {
        // 基础情况：如果只剩下一个元素，返回该元素和最小值
        if (low == high) {
            return new int[]{arr[low], Integer.MIN_VALUE};
        }

        // 如果有两个元素，返回较大和较小的值
        if (low + 1 == high) {
            if (arr[low] > arr[high]) {
                return new int[]{arr[low], arr[high]};
            } else {
                return new int[]{arr[high], arr[low]};
            }
        }

        // 分割
        int mid = low + (high - low) / 2;

        // 递归查找左右部分的最大和第二大元素
        int[] leftResult = findSecondLargestRec(arr, low, mid);
        int[] rightResult = findSecondLargestRec(arr, mid + 1, high);

        // 计算最大值和第二大值
        int max = Math.max(leftResult[0], rightResult[0]);
        int secondLargest = Math.min(Math.max(leftResult[0], leftResult[1]), Math.max(rightResult[0], rightResult[1]));

        return new int[]{max, secondLargest};
    }

    // 主程序
    public static void main(String[] args) {
        int[] arr = {3, 7, 5, 1, 4, 2, 6};
        int secondLargest = findSecondLargest(arr);
        System.out.println("The second largest element is: " + secondLargest);
    }
}



