package com.kim.thread;

import com.kim.utils.ThreadUtil;

public class JoinDemo {
    public static final int SLEEP_GAP = 5000;
    public static final int MAX_TURN = 50;

    static class SleepThread extends Thread {
        static int threadSeqNumber = 1;
        public SleepThread() {
            super("sleepThread-" + threadSeqNumber);
            threadSeqNumber++;
        }

        @Override
        public void run() {
            try {
                System.out.println(getName() + " 进入睡眠.");
                Thread.sleep(SLEEP_GAP);
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.out.println(getName() + " 发生被异常打断.");
                return;
            }
            System.out.println(getName() + " 运行结束.");
        }
    }

    public static void main(String[] args) {
        SleepThread thread1 = new SleepThread();
        System.out.println(ThreadUtil.getCurThreadName() +  "启动 thread1.");
        thread1.start();

        try {
            thread1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(ThreadUtil.getCurThreadName() + "启动 thread2");
        SleepThread thread2 = new SleepThread();
        thread2.start();
        try {
            thread2.join(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(ThreadUtil.getCurThreadName() + "线程运行结束.");
    }
}
