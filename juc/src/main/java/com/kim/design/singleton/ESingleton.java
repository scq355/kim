package com.kim.design.singleton;

/**
 * volatile + 双重检查锁
 */
public class ESingleton {
    private static volatile ESingleton instance;

    private ESingleton() {}

    static synchronized ESingleton getInstance() {
        if (instance == null) {
            synchronized (ESingleton.class) {
                if (instance == null) {
                    instance = new ESingleton();
                }
            }
        }
        return instance;
    }
}
