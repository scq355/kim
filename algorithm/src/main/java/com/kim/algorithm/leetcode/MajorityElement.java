package com.kim.algorithm.leetcode;

public class MajorityElement {

    public static void main(String[] args) {
        System.out.println(majorityElement(new int[]{6, 5, 5}));
    }

    public static int majorityElement(int[] nums) {
        int majority = nums[0];
        int count = 1;
        int[] tags = new int[nums.length / 2 + 1];
        int values = 0;
        boolean skip = false;
            for (int i = 0; i < nums.length; i++) {
            for (int j = 0; j < values; j++) {
                if (tags[j] == nums[i]) {
                    skip = true;
                    break;
                }
            }
            if (skip) {
                skip = false;
                continue;
            }
            for (int j = 0; j < nums.length; j++) {
                if (nums[i] == nums[j] && i != j) {
                    count++;
                }
            }
            if (count > nums.length / 2) {
                majority = nums[i];
            }
            tags[values] = nums[i];
            values++;
            count = 1;
        }
        return majority;
    }
}
