package com.kim.atomic;

import org.junit.Test;
import com.kim.utils.Print;
import com.kim.utils.ThreadUtil;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;

public class LongAdderVSAtomicLongTest {
    final int TURNS = 100000000;

    @Test
    public void testAtomicLong() {
        final int TASK_AMOUNT = 10;
        ExecutorService pool = ThreadUtil.getCpuIntenseTargetThreadPool();
        CountDownLatch latch = new CountDownLatch(TASK_AMOUNT);
        AtomicLong atomicLong = new AtomicLong(0);
        long start = System.currentTimeMillis();
        for (int i = 0; i < TASK_AMOUNT; i++) {
            pool.submit(() -> {
                try {
                    for (int j = 0; j < TURNS; j++) {
                        atomicLong.incrementAndGet();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                latch.countDown();
            });
        }

        try {
            latch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        float time = (System.currentTimeMillis() - start) / 1000F;
        Print.tcfo("运行耗时=" + time);
        Print.tcfo("累加结果为=" + atomicLong.get());
    }

    @Test
    public void testLongAdder() {
        final int TASK_AMOUNT = 10;

        ThreadPoolExecutor pool = ThreadUtil.getCpuIntenseTargetThreadPool();
        LongAdder longAdder = new LongAdder();
        CountDownLatch latch = new CountDownLatch(TASK_AMOUNT);
        long start = System.currentTimeMillis();
        for (int i = 0; i < TASK_AMOUNT; i++) {
            pool.submit(() -> {
                try {
                    for (int j = 0; j < TURNS; j++) {
                        longAdder.add(1);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                latch.countDown();
            });
        }

        try {
            latch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        float time = (System.currentTimeMillis() - start) / 1000F;

        Print.tcfo("运行耗时=" + time);
        Print.tcfo("累加结果为=" + longAdder.longValue());
    }
}
