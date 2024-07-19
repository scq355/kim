package com.kim.locks;

import com.kim.utils.Print;
import com.kim.utils.ThreadUtil;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantCommunicateTest {
    static Lock lock = new ReentrantLock();

    private static Condition condition = lock.newCondition();

    static class WaitTarget implements Runnable {
        @Override
        public void run() {
            lock.lock();
            try {
                Print.tcfo("我是等待方");
                condition.await();
                Print.tco("收到通知，等待方继续执行");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }

    static class NotifyTarget implements Runnable {
        @Override
        public void run() {
            lock.lock();
            try {
                Print.tcfo("我是通知方");
                condition.signal();
                Print.tco("发出通知了，但是线程还没有立马释放锁");
            } finally {
                lock.unlock();
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
