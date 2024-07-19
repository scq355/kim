package com.kim.design.singleton;

/**
 * 静态内部类懒汉式
 */
public class Singleton {
    private static class LazyHolder {
        private static final Singleton INSTANCE = new Singleton();
    }

    private Singleton() {}

    public static final Singleton getInstance() {
        return LazyHolder.INSTANCE;
    }
}
