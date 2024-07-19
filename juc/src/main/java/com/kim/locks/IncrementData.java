package com.kim.locks;

import com.kim.utils.Print;
import com.kim.utils.ThreadUtil;

import java.util.concurrent.locks.Lock;

public class IncrementData {

    public static int sum = 0;

    public static void lockAndFastIncrement(Lock lock) {
        lock.lock();
        try {
            sum++;
        } finally {
            lock.unlock();
        }
    }

    public static void lockAndIncrement(Lock lock) {
        Print.synTco("-- 开始抢占锁");
        lock.lock();
        try {
            Print.synTco("-- 抢到了锁");
            sum++;
        } finally {
            lock.unlock();
        }
    }

    public static void lockInterruptiblyAndIncrease(Lock lock) {
        try {
            lock.lockInterruptibly();
        } catch (InterruptedException e) {
            Print.synTco("抢占被中断，抢锁失败");
            return;
        }

        try {
            Print.synTco("抢到了锁，同步执行1s");
            ThreadUtil.sleepMilliSeconds(1000);
            sum++;
            if (Thread.currentThread().isInterrupted()) {
                Print.synTco("同步执行被中断");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public static void tryLockAndIncrease(Lock lock) {
        if (lock.tryLock()) {
            Print.synTco("线程抢到了锁");
            try {
                Thread.sleep(100);
                sum++;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }finally {
                lock.unlock();
            }
        } else {
            Print.synTco("线程抢锁失败");
        }
    }
}
