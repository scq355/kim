package com.kim.thread;

import com.kim.utils.ThreadUtil;

public class SleepDemo {
    public static final int SLEEP_GAP = 5000;
    public static final int MAX_TURN = 50;

    static class SleepThread extends Thread {
        static int threadSqpNumber = 1;
        public SleepThread() {
            super("sleepThread-" + threadSqpNumber);
            threadSqpNumber++;
        }

        @Override
        public void run() {
            try {
                for (int i = 0; i < MAX_TURN; i++) {
                    System.out.println(getName() + ", 睡眠轮次：" + i);
                    Thread.sleep(SLEEP_GAP);
                }
            } catch (InterruptedException e) {
                System.out.println(getName() + " 发生异常被中断.");
                throw new RuntimeException(e);
            }
            System.out.println(getName() + " 运行结束.");
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            Thread thread = new SleepThread();
            thread.start();
        }
        System.out.println(ThreadUtil.getCurThreadName() + " 运行结束.");
    }
}
