package com.kim.happensbefore;

import org.junit.Test;
import com.kim.utils.Print;
import com.kim.utils.ThreadUtil;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;

public class VolatileDemo {
    private volatile long value;

    @Test
    public void testAtomicLong() {
        final int TASK_AMOUNT = 10;

        ExecutorService pool = ThreadUtil.getCpuIntenseTargetThreadPool();
        final int TURN = 10000;
        CountDownLatch latch = new CountDownLatch(TASK_AMOUNT);
        long start = System.currentTimeMillis();
        for (int i = 0; i < TASK_AMOUNT; i++) {
            pool.submit(() -> {
                try {
                    for (int j = 0; j < TURN; j++) {
                        value++;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                latch.countDown();
            });
        }

        float time = (System.currentTimeMillis() - start) / 1000F;
        Print.tcfo("运行时长为：" + time);
        Print.tcfo("累加结果为：" + value);
        Print.tcfo("与预期相差：" + (TURN * TASK_AMOUNT - value)) ;

    }
}
