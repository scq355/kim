package com.kim.thread;

public class EmptyThreadDemo {
    public static void main(String[] args) {
        Thread thread = new Thread();
        System.out.println("线程名称：" + thread.getName());
        System.out.println("线程ID：" + thread.getId());
        System.out.println("线程状态：" + thread.getState());
        System.out.println("线程优先级：" + thread.getPriority());
        thread.start();
    }
}
