package com.kim.locks;

import com.kim.utils.Print;
import com.kim.utils.ThreadUtil;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;

public class CountdownLatchDemo {

    private static final int N = 100;

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(N);
        Executor executor = ThreadUtil.getCpuIntenseTargetThreadPool();
        for (int i = 1; i <= N; i++) {
            executor.execute(new Person(latch, i));
        }

        latch.await();
        Print.tcfo("人到齐，开车");
    }

    static class Person implements Runnable {

        private final CountDownLatch doneSignal;
        private final int i;

        Person(CountDownLatch doneSignal, int i) {
            this.doneSignal = doneSignal;
            this.i = i;
        }

        @Override
        public void run() {
            try {
                Print.tcfo("第" + i + "个人到达");
                doneSignal.countDown();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
