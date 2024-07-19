package com.kim.locks;

import com.kim.utils.Print;
import com.kim.utils.ThreadUtil;

import java.util.concurrent.locks.Lock;

public class TwoLockDemo {
    public static void useTwoLockInterruptiblyLock(Lock lock1, Lock lock2) {
        String lock1Name = lock1.toString().replace("java.util.concurrent.locks", "");
        String lock2Name = lock2.toString().replace("java.util.concurrent.locks", "");

        Print.synTco("开始抢第一把锁：" + lock1Name);
        try {
            lock1.lockInterruptibly();
        } catch (InterruptedException e) {
            Print.synTco("被中断，抢第一把锁失败：" + lock1Name);
            return;
        }
        try {
            Print.synTco("抢到了第一把锁：" + lock1Name);
            Thread.sleep(1000 * 10);
            Print.synTco("开始抢第二把锁：" + lock2Name);
            try {
                lock2.lockInterruptibly();
            } catch (InterruptedException e) {
                Print.synTco("被中断，抢第二把锁失败：" + lock2Name);
                return;
            }

            try {
                Print.synTco("抢到了第二把锁：" + lock2Name);
                Print.synTco("do something");
                ThreadUtil.sleepMilliSeconds(1000);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock2.unlock();
                Print.synTco("释放第二把锁：" + lock2Name);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock1.unlock();
            Print.synTco("释放第一把锁：" + lock1Name);
        }
    }
}
