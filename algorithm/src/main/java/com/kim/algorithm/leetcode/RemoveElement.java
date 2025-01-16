package com.kim.algorithm.leetcode;

public class RemoveElement {
    public static void main(String[] args) {
        System.out.println(removeElement(new int[]{0,1,2,2,3,0,4,2}, 2));
    }
    public static int removeElement(int[] nums, int val) {
        int result = 0;
        for (int i = 0, k = 0; k < nums.length; k++) {
            if (nums[i] == val) {
                result++;
                int j = i;
                for (; j < nums.length - result; j++) {
                    nums[j] = nums[j + 1];
                }
            } else {
                i++;
            }
        }
        return nums.length - result;
    }
}
