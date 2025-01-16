package com.kim.algorithm.leetcode;

import java.util.Arrays;

public class Rotate {

    public static void main(String[] args) {
        rotate2(new int[]{-1,-100,3,99}, 2);
    }

    public static void rotate2(int[] nums, int k) {
        if (k > nums.length) {
            k = k % nums.length;
        }
        for (int i = 0; i < k; i++) {
            int temp = nums[nums.length - 1];
            for (int j = nums.length - 1; j > 0; j--) {
                nums[j] = nums[j - 1];
            }
            nums[0] = temp;
        }
        System.out.println(Arrays.toString(nums));
    }

    public static void rotate(int[] nums, int k) {
        if (k > nums.length) {
            k = k % nums.length;
        }
        int[] temp = new int[k];
        for (int i = nums.length - k, j = 0; i < nums.length; i++, j++) {
            temp[j] = nums[i];
        }
        for (int i = nums.length - k - 1; i >= 0; i--) {
            nums[i + k] = nums[i];
        }
        for (int i = 0; i < k; i++) {
            nums[i] = temp[i];
        }
        System.out.println(Arrays.toString(nums));
    }
}
