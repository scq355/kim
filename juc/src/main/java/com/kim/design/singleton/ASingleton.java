package com.kim.design.singleton;

/**
 * 懒汉单例
 */
public class ASingleton {
    private static ASingleton instance;

    private ASingleton() {}

    static ASingleton getInstance() {
        if (instance == null) {
            instance = new ASingleton();
        }
        return instance;
    }
}
