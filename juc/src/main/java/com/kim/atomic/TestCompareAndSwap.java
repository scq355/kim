package com.kim.atomic;

import org.junit.Test;
import org.openjdk.jol.info.ClassLayout;
import com.kim.utils.Print;
import com.kim.utils.ThreadUtil;
import sun.misc.Unsafe;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

import static com.kim.utils.JvmUtil.getUnsafe;

public class TestCompareAndSwap {

    static class OptimisticLockingPlus {
        private static final int THREAD_COUNT = 10;

        private volatile int value;

        private static final Unsafe unsafe = getUnsafe();

        private static final long valueOffset;

        private static final AtomicInteger failure = new AtomicInteger(0);

        static {
            try {
                valueOffset = unsafe.objectFieldOffset(OptimisticLockingPlus.class.getDeclaredField("value"));
                Print.tcfo("valueOffset=" + valueOffset);
            } catch (NoSuchFieldException e) {
                throw new RuntimeException(e);
            }
        }

        public final boolean unSafeCompareAndSet(int oldValue, int newValue) {
            return unsafe.compareAndSwapInt(this, valueOffset, oldValue, newValue);
        }

        public void selfPlus() {
            int oldValue = value;
            int i = 0;
            do {
                oldValue = value;
                if (i++ > 1) {
                    failure.incrementAndGet();
                }
            } while (!unSafeCompareAndSet(oldValue, oldValue + 1));
        }

        public static void main(String[] args) throws InterruptedException {
            OptimisticLockingPlus cas = new OptimisticLockingPlus();
            CountDownLatch latch = new CountDownLatch(THREAD_COUNT);
            for (int i = 0; i < THREAD_COUNT; i++) {
                ThreadUtil.getMixedTargetThreadPool().submit(() -> {
                    for (int j = 0; j < 1000; j++) {
                        cas.selfPlus();
                    }
                    latch.countDown();
                });
            }
            latch.await();
            Print.tco("累加和=" + cas.value);
            Print.tco("失败次数=" + cas.failure.get());
        }
    }

    @Test
    public void printObjectStructure() {
        OptimisticLockingPlus plus = new OptimisticLockingPlus();
        plus.value = 100;
        String printable = ClassLayout.parseInstance(plus).toPrintable();
        Print.fo("plus=" + printable);
    }
}
