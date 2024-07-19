package com.kim;

import lombok.Data;

public class TestDynamicBind {
    public static void main(String[] args) {
        Worker w = new Worker();
        w.hello();
        w.hi();

        // 多态
        Person person = new Worker();
        // 静态绑定属性
        System.out.println(person.getName());
        // 动态绑定方法
        person.hello();
    }
}

@Data
class Person {
    private String name = "person";

    public void hello() {
        System.out.println("hello person");
    }
}

class Worker extends Person {
    private String name = "worker";

    public void hello() {
        System.out.println("hello worker");
    }

    public void hi() {
        System.out.println("hi worker");
    }
}
