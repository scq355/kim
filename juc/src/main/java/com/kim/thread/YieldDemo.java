package com.kim.thread;

import org.junit.Test;
import com.kim.utils.ThreadUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class YieldDemo {
    public static final int MAX_TURN = 100;
    public static AtomicInteger index = new AtomicInteger(0);

    private static Map<String, AtomicInteger> metric = new HashMap<>();

    private static void printMetric() {
        System.out.println("metric=" + metric);
    }

    static class YieldThread extends Thread {
        static int threadSeqNumber = 1;
        public YieldThread() {
            super("sleepThread-" + threadSeqNumber);
            threadSeqNumber++;
            metric.put(this.getName(), new AtomicInteger(0));
        }

        @Override
        public void run() {
            for (int i = 1; i < MAX_TURN && index.get() < MAX_TURN; i++) {
                System.out.println(ThreadUtil.getCurThreadName() +  "  线程优先级：" + getPriority());
                index.incrementAndGet();
                metric.get(this.getName()).incrementAndGet();
                if (i % 2 == 0) {
                    Thread.yield();
                }
            }
            printMetric();
            System.out.println(getName() + " 运行结束.");
        }
    }


    @Test
    public void test() throws InterruptedException {
        YieldThread thread1 = new YieldThread();
        thread1.setPriority(Thread.MAX_PRIORITY);
        YieldThread thread2 = new YieldThread();
        thread2.setPriority(Thread.MIN_PRIORITY);
        System.out.println("启动线程");
        thread1.start();
        thread2.start();
        Thread.sleep(100 * 1000L);
    }
}
