package com.kim;

public class TestBreak {
    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            if (i == 3) {
                break;
            } else {
                System.out.println(i);
            }
        }

        try {
            for (int i = 0; i < 5; i++) {
                if (i == 3) {
                    throw new RuntimeException("只是退出循环");
                } else {
                    System.out.println(i);
                }
            }
        } catch (Exception e) {
            System.out.println("退出循环");
        }
    }
}
