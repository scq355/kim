package com.kim.design.singleton;

/**
 * 饿汉单例
 */
public class Singleton1 {
    private Singleton1() {}

    private static final Singleton1 single = new Singleton1();

    public static Singleton1 getInstance() {
        return single;
    }
}
