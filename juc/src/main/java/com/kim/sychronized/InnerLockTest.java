package com.kim.sychronized;

import org.junit.Test;
import org.openjdk.jol.vm.VM;
import com.kim.utils.Print;

import java.util.concurrent.CountDownLatch;

import static com.kim.utils.ThreadUtil.sleepMilliSeconds;

public class InnerLockTest {
    final int MAX_TREAD = 10;
    final int MAX_TURN = 1000;

    @Test
    public void showNoLockObject() {
        Print.fo(VM.current().details());

        ObjectLock objectLock = new ObjectLock();
        Print.fo("object status:");
        objectLock.printSelf();
    }

    @Test
    public void showBiasedLock() throws InterruptedException {
        Print.tcfo(VM.current().details());

        sleepMilliSeconds(5000);
        ObjectLock lock = new ObjectLock();
        Print.tcfo("抢占锁前，lock状态：");
        lock.printObjectStruct();

        sleepMilliSeconds(5000);
        CountDownLatch latch = new CountDownLatch(1);
        Runnable runnable = () -> {
            for (int i = 0; i < MAX_TURN; i++) {
                synchronized (lock) {
                    lock.increase();
                    if (i == MAX_TURN / 2) {
                        Print.tcfo("占有锁，lock状态：");
                        lock.printObjectStruct();
                    }
                }
                sleepMilliSeconds(10);
            }
            latch.countDown();
        };
        new Thread(runnable, "biased-demo-thread").start();
        latch.await();
        Print.tcfo("释放锁后，lock的状态：");
        lock.printObjectStruct();
    }

    @Test
    public void showLightweightLock() throws InterruptedException {
        Print.tcfo(VM.current().details());
        sleepMilliSeconds(5000);
        ObjectLock lock = new ObjectLock();
        Print.tcfo("抢占锁前，lock的状态：");
        lock.printObjectStruct();

        CountDownLatch latch = new CountDownLatch(2);
        Runnable runnable = () -> {
            for (int i = 0; i < MAX_TURN; i++) {
                synchronized (lock) {
                    lock.increase();
                    if (i == 0) {
                        Print.tcfo("第一个线程占有锁，lock的状态：");
                        lock.printObjectStruct();
                    }
                }
            }
            latch.countDown();

            for (; ;) {
                sleepMilliSeconds(1);
            }
        };

        new Thread(runnable).start();
        sleepMilliSeconds(1000);

        Runnable lightWeightRunnable = () -> {
            for (int i = 0; i < MAX_TURN; i++) {
                synchronized (lock) {
                    lock.increase();
                    if (i == MAX_TURN / 2) {
                        Print.tcfo("第二个线程占有锁，lock的状态：");
                        lock.printObjectStruct();
                    }
                    sleepMilliSeconds(1);
                }
            }
            latch.countDown();
        };

        new Thread(lightWeightRunnable).start();

        latch.await();
        sleepMilliSeconds(2000);
        Print.tcfo("释放锁后，lock状态");
        lock.printObjectStruct();
    }


    @Test
    public void showHeavyweightLock() throws InterruptedException {
        Print.tcfo(VM.current().details());
        sleepMilliSeconds(5000);
        ObjectLock lock = new ObjectLock();
        Print.tcfo("抢占锁前，lock的状态：");
        lock.printObjectStruct();

        sleepMilliSeconds(5000);
        CountDownLatch latch = new CountDownLatch(3);
        Runnable runnable = () -> {
            for (int i = 0; i < MAX_TURN; i++) {
                synchronized (lock) {
                    lock.increase();
                    if (i == 0) {
                        Print.tcfo("第一个线程占有锁，lock的状态：");
                        lock.printObjectStruct();
                    }
                }
            }
            latch.countDown();

            for (; ;) {
                sleepMilliSeconds(1);
            }
        };

        new Thread(runnable).start();
        sleepMilliSeconds(1000);
        Runnable lightWeightRunnable = () -> {
            for (int i = 0; i < MAX_TURN; i++) {
                synchronized (lock) {
                    lock.increase();
                    if (i == 0) {
                        Print.tcfo("占有锁，lock的状态：");
                        lock.printObjectStruct();
                    }
                    sleepMilliSeconds(1);
                }
            }
            latch.countDown();
        };

        new Thread(lightWeightRunnable, "抢锁线程1").start();
        sleepMilliSeconds(100);
        new Thread(lightWeightRunnable, "抢锁线程2").start();

        latch.await();
        sleepMilliSeconds(2000);
        Print.tcfo("释放锁后，lock状态");
        lock.printObjectStruct();
    }
}
