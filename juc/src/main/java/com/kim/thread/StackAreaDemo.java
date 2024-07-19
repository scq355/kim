package com.kim.thread;

public class StackAreaDemo {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("当前线程名：" + Thread.currentThread().getName());
        System.out.println("线程ID：" + Thread.currentThread().getId());
        System.out.println("线程状态：" + Thread.currentThread().getState());
        System.out.println("线程优先级：" + Thread.currentThread().getPriority());

        int a = 1, b = 1;
        int c = a / b;
        anotherFun();
        Thread.sleep(10000000);
    }

    private static void anotherFun() {
        int a = 1, b = 1;
        int c = a / b;
        anotherFun2();
    }

    private static void anotherFun2() {
        int a = 1, b = 1;
        int c = a / b;
    }
}
