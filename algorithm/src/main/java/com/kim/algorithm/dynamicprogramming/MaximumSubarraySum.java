package com.kim.algorithm.dynamicprogramming;

public class MaximumSubarraySum {

    // 方法实现最大子段和
    public static int maxSubArray(int[] nums) {
        int maxSum = nums[0]; // 初始化最大和
        int currentSum = nums[0]; // 初始化当前和

        for (int i = 1; i < nums.length; i++) {
            currentSum = Math.max(nums[i], currentSum + nums[i]); // 更新当前和
            maxSum = Math.max(maxSum, currentSum); // 更新最大和
        }

        return maxSum; // 返回最大子段和
    }

    // 主程序
    public static void main(String[] args) {
        int[] nums = {-2, 1, -3, 4, -1, 2, 1, -5, 4}; // 示例数组

        int maxSum = maxSubArray(nums);
        System.out.println("Maximum subarray sum: " + maxSum);
    }
}

