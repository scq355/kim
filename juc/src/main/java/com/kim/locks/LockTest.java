package com.kim.locks;

import org.junit.Test;
import com.kim.utils.Print;
import com.kim.utils.ThreadUtil;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockTest {

    @Test
    public void testReentrantLock() {
        final int TURNS = 1000;
        final int THREADS = 10;
        ExecutorService pool = Executors.newFixedThreadPool(THREADS);
        ReentrantLock lock = new ReentrantLock();
        CountDownLatch latch = new CountDownLatch(THREADS);
        long start = System.currentTimeMillis();
        for (int i = 0; i < THREADS; i++) {
            pool.submit(() -> {
                try {
                    for (int j = 0; j < TURNS; j++) {
                        IncrementData.lockAndFastIncrement(lock);
                    }
                    Print.tco("本线程累加完成");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                latch.countDown();
            });
        }

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        float time = (System.currentTimeMillis() - start) / 1000F;
        Print.tcfo("运行时长为：" + time);
        Print.tcfo("累加结果为：" + IncrementData.sum);
    }

    @Test
    public void testCLHLockCapability() {
        final int TURN = 100000;

        final int THREADS = 10;

        ExecutorService pool = Executors.newFixedThreadPool(THREADS);
        Lock lock = new CLHLock();
        CountDownLatch latch = new CountDownLatch(THREADS);
        long start = System.currentTimeMillis();
        for (int i = 0; i < THREADS; i++) {
            pool.submit(() -> {
                for (int j = 0; j < TURN; j++) {
                    IncrementData.lockAndFastIncrement(lock);
                }
                Print.tco("本线程完成累加");
                latch.countDown();
            });
        }

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        float time = (System.currentTimeMillis() - start) / 1000F;
        Print.tcfo("运行时长为：" + time);
        Print.tcfo("累加结果为：" + IncrementData.sum);
    }

    @Test
    public void testNotFairLock() throws InterruptedException {
        ReentrantLock lock = new ReentrantLock(false);
        Runnable runnable = () -> IncrementData.lockAndIncrement(lock);

        Thread[] threads = new Thread[4];
        for (int i = 0; i < 4; i++) {
            threads[i] = new Thread(runnable, "线程" + i);
        }
        for (int i = 0; i < 4; i++) {
            threads[i].start();
        }
        Thread.sleep(Integer.MAX_VALUE);
    }


    @Test
    public void testFairLock() throws InterruptedException {
        Lock lock = new CLHLock();
        Runnable runnable = () -> IncrementData.lockAndIncrement(lock);

        Thread[] threads = new Thread[4];
        for (int i = 0; i < 4; i++) {
            threads[i] = new Thread(runnable, "线程" + i);
        }
        for (int i = 0; i < 4; i++) {
            threads[i].start();
        }
        Thread.sleep(Integer.MAX_VALUE);
    }

    @Test
    public void testInterruptLock() throws InterruptedException {
        ReentrantLock lock = new ReentrantLock();
        Runnable runnable = () -> IncrementData.lockInterruptiblyAndIncrease(lock);

        Thread thread1 = new Thread(runnable, "thread-1");
        Thread thread2 = new Thread(runnable, "thread-2");

        thread1.start();
        thread2.start();

        ThreadUtil.sleepMilliSeconds(100);
        Print.synTco("等待100ms，中断两个线程");
        thread1.interrupt();
        thread2.interrupt();

        Thread.sleep(Integer.MAX_VALUE);
    }

    public static ThreadMXBean mxBean = ManagementFactory.getThreadMXBean();

    @Test
    public void testDeadLock() throws InterruptedException {
        ReentrantLock lock1 = new ReentrantLock();
        ReentrantLock lock2 = new ReentrantLock();

        Runnable runnable1 = () -> TwoLockDemo.useTwoLockInterruptiblyLock(lock1, lock2);
        Runnable runnable2 = () -> TwoLockDemo.useTwoLockInterruptiblyLock(lock2, lock1);

        Thread thread1 = new Thread(runnable1, "thread1");
        Thread thread2 = new Thread(runnable2, "thread2");

        thread1.start();
        thread2.start();

        Thread.sleep(10000);
        Print.tcfo("等待2s, 开始死锁检测处理");

        long[] deadlockedThreads = mxBean.findDeadlockedThreads();

        if (deadlockedThreads.length > 0) {
            Print.tcfo("发生死锁，输出死锁线程的信息");
            for (long pid : deadlockedThreads) {
                ThreadInfo threadInfo = mxBean.getThreadInfo(pid, Integer.MAX_VALUE);
                Print.tcfo(threadInfo);
            }
            Print.tcfo("中断一个死锁：" + thread1.getName());
            thread1.interrupt();
        }
    }


    @Test
    public void testMockLock() {
        final int TURNS = 1000;
        final int THREADS = 10;

        ExecutorService pool = Executors.newFixedThreadPool(THREADS);
        Lock lock = new SimpleMockLock();
        CountDownLatch latch = new CountDownLatch(THREADS);

        long start = System.currentTimeMillis();
        for (int i = 0; i < THREADS; i++) {
            pool.submit(() -> {
                try {
                    for (int j = 0; j < TURNS; j++) {
                        IncrementData.lockAndFastIncrement(lock);
                    }
                    Print.tcfo("本线程累加完成");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                latch.countDown();
            });
        }
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        float time = (System.currentTimeMillis() - start) / 1000F;
        Print.tcfo("运行时长为：" + time);
        Print.tcfo("累加结果为：" + IncrementData.sum);

    }

}
