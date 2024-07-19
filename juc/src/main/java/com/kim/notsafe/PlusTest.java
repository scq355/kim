package com.kim.notsafe;

import org.junit.Test;
import com.kim.utils.Print;

import java.util.concurrent.CountDownLatch;

public class PlusTest {

    final int MAX_TREAD = 10;
    final int MAX_TURN = 1000;

    @Test
    public void testNotSafePlus() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(MAX_TREAD);
        NotSafePlus counter = new NotSafePlus();

        Runnable runnable = () -> {
            for (int i = 0; i < MAX_TURN; i++) {
                counter.selfPlus();
            }
            latch.countDown();
        };
        for (int i = 0; i < MAX_TREAD; i++) {
            new Thread(runnable).start();
        }
        latch.await();
        Print.tcfo("理论结果：" + MAX_TURN * MAX_TREAD);
        Print.tcfo("实际结果：" + counter.getAmount());
    }
}
