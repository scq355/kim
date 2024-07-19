package com.kim.design.singleton;

public class BSingleton {
    private static BSingleton instance;

    private BSingleton() {}

    static synchronized BSingleton getInstance() {
        if (instance == null) {
            instance = new BSingleton();
        }
        return instance;
    }
}
