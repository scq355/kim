package com.kim.thread;

import com.kim.utils.Print;
import com.kim.utils.ThreadUtil;

public class WaitNotifyDemo {
    static final Object locko = new Object();

    static class WaitTarget implements Runnable {

        @Override
        public void run() {
            synchronized (locko) {
                try {
                    Print.tcfo("启动等待");
                    locko.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Print.tcfo("收到通知，当前线程继续执行");
            }
        }
    }


    static class NotifyTarget implements Runnable {

        @Override
        public void run() {
            synchronized (locko) {
                Print.consoleInput();
                locko.notifyAll();
                Print.tcfo("发出通知了，但是线程还没有立马释放锁");
            }
        }
    }

    public static void main(String[] args) {
        Thread waitThread = new Thread(new WaitTarget(), "WaitThread");
        waitThread.start();
        ThreadUtil.sleepSeconds(1);

        Thread notifyThread = new Thread(new NotifyTarget(), "NotifyThread");
        notifyThread.start();
    }
}
