package com.kim.notsafe;

public class SafeStaticMethodPlus {
    private static Integer amount = 0;

    public static synchronized void selfPlus() {
        amount++;
    }
}
