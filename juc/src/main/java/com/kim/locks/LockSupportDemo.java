package com.kim.locks;

import org.junit.Test;
import com.kim.utils.Print;
import com.kim.utils.ThreadUtil;

import java.util.concurrent.locks.LockSupport;

public class LockSupportDemo {

    public static class ChangeObjectThread extends Thread {
        public ChangeObjectThread(String name) {
            super(name);
        }

        @Override
        public void run() {
            Print.tco("即将进入无限时阻塞");
            LockSupport.park();
            if (Thread.currentThread().isInterrupted()) {
                Print.tco("被中断了，但仍然会继续执行");
            } else {
                Print.tco("被重新唤醒了");
            }
        }
    }

    @Test
    public void testLockSupport() {
        ChangeObjectThread thread1 = new ChangeObjectThread("线程1");
        ChangeObjectThread thread2 = new ChangeObjectThread("线程2");

        thread1.start();
        ThreadUtil.sleepSeconds(10);

        thread2.start();
        ThreadUtil.sleepSeconds(10);
        // 中断1
        thread1.interrupt();
        // 唤醒2
        LockSupport.unpark(thread2);
    }

    @Test
    public void testLockSupport2() {
        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Print.tco("即将进入无限时阻塞");
            LockSupport.park();
            Print.tco("被重新唤醒了");
        }, "演示线程");
        thread.start();
        LockSupport.unpark(thread);
        LockSupport.unpark(thread);
        ThreadUtil.sleepSeconds(2);
        LockSupport.unpark(thread);
    }
}
