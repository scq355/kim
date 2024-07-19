package com.kim.locks;

import org.junit.Test;
import com.kim.utils.DateUtil;
import com.kim.utils.Print;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

public class SemaphoreTest {

    @Test
    public void testShareLock() throws InterruptedException {
        final int USER_TOTAL = 10;
        final int PERMIT_TOTAL = 2;

        final CountDownLatch latch = new CountDownLatch(USER_TOTAL);
        final Semaphore semaphore = new Semaphore(PERMIT_TOTAL);

        AtomicInteger index = new AtomicInteger(0);

        Runnable runnable = () -> {
            try {
                semaphore.acquire(1);
                Print.tcfo(DateUtil.getNow() + " 受理处理中，服务号：" + index.incrementAndGet());
                Thread.sleep(1000);
                semaphore.release(1);
            } catch (Exception e) {
                e.printStackTrace();
            }
            latch.countDown();
        };

        Thread[] threads = new Thread[USER_TOTAL];
        for (int i = 0; i < USER_TOTAL; i++) {
            threads[i] = new Thread(runnable, "线程" + i);
        }

        for (int i = 0; i < USER_TOTAL; i++) {
            threads[i].start();
        }

        latch.await();

    }
}
