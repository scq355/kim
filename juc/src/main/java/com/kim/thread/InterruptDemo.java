package com.kim.thread;

import org.junit.Test;
import com.kim.utils.ThreadUtil;

public class InterruptDemo {
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

    public static void main(String[] args) throws InterruptedException {
        SleepThread thread1 = new SleepThread();
        thread1.start();
        SleepThread thread2 = new SleepThread();
        thread2.start();

        Thread.sleep(2 * 1000L);
        thread1.interrupt();
        Thread.sleep(5 * 1000L);
        thread2.interrupt();
        Thread.sleep(1000L);
        System.out.println("程序运行结束.");
    }

    @Test
    public void testInterrupt() throws InterruptedException {
        Thread thread = new Thread() {
            @Override
            public void run() {
                System.out.println("线程启动了");
                while (true) {
                    System.out.println(Thread.currentThread().isInterrupted());
                    ThreadUtil.sleepMilliSeconds(5000);
                    if (Thread.currentThread().isInterrupted()) {
                        System.out.println("线程结束了");
                        return;
                    }
                }
            }
        };
        thread.start();
        Thread.sleep(2 * 1000L);
        thread.interrupt();
        Thread.sleep(2 * 1000L);
        thread.interrupt();
    }
}
