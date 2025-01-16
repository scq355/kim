package com.kim.algorithm.leetcode;

public class RemoveDuplicates {

    public static void main(String[] args) {
        System.out.println(removeDuplicates2(new int[]{0,0,1,1,1,2,3,3,3,4,4}));
//        System.out.println(removeDuplicates(new int[]{1, 1, 2}));
    }


    public static int removeDuplicates2(int[] nums) {
        int count = 0;
        boolean plus = false;
        for (int i = 0; i < nums.length - count; i++) {
            int moreThan2 = 0;
            for (int j = i + 1; j < nums.length - count; ) {
                if (nums[i] == nums[j]) {
                    moreThan2++;
                    if (moreThan2 >= 2) {
                        count++;
                        plus = true;
                        for (int k = j; k < nums.length - count; k++) {
                            nums[k] = nums[k + 1];
                        }
                    } else {
                        j++;
                    }
                } else {
                    break;
                }
            }
            if (plus) {
                i++;
            }
            plus = false;
        }
        return nums.length - count;
    }

    public static int removeDuplicates(int[] nums) {
        int count = 0;
        for (int i = 0; i < nums.length - count; i++) {
            for (int j = i + 1; j < nums.length - count; ) {
                if (nums[i] == nums[j]) {
                    count++;
                    for (int k = i + 1; k < nums.length - count; k++) {
                        nums[k] = nums[k + 1];
                    }
                } else {
                    break;
                }
            }
        }
        return nums.length - count;
    }
}
