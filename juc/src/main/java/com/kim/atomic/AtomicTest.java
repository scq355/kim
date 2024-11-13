package com.kim.atomic;

import org.junit.Test;
import com.kim.entity.User;
import com.kim.utils.Print;
import com.kim.utils.ThreadUtil;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.*;

public class AtomicTest {

    private static final int THREAD_COUNT = 10;


    @Test
    public void atomicIntegerTest() {
        int tempValue = 0;
        AtomicInteger i = new AtomicInteger(0);
        tempValue = i.getAndSet(3);
        Print.fo("tempValue=" + tempValue + "; i=" + i.get());

        tempValue = i.getAndIncrement();
        Print.fo("tempValue=" + tempValue + "; i=" + i.get());


        tempValue = i.getAndAdd(5);
        Print.fo("tempValue=" + tempValue + "; i=" + i.get());


        boolean flag = i.compareAndSet(9, 100);
        Print.fo("flag=" + flag + "; i=" + i.get());
    }

    @Test
    public void testAtomicArray() {
        int tempValue = 0;
        int[] array = {1, 2, 3, 4, 5, 6};
        AtomicIntegerArray i = new AtomicIntegerArray(array);

        tempValue = i.getAndSet(0, 2);
        Print.fo("tempValue=" + tempValue + " i=" + i);

        tempValue = i.getAndIncrement(0);
        Print.fo("tempValue=" + tempValue + " i=" + i);

        tempValue = i.getAndAdd(0, 5);
        Print.fo("tempValue=" + tempValue + " i=" + i);
    }

    @Test
    public void testAtomicReference() {
        AtomicReference<User> userRef = new AtomicReference<>();
        User user = new User("1", "张三");
        userRef.set(user);
        Print.tco("userRef=" + userRef.get());

        User updateUser = new User("2", "李四");
        boolean success = userRef.compareAndSet(user, updateUser);

        Print.tco("cas result=" + success);
        Print.tco("after cas, userRef=" + userRef.get());
    }

    @Test
    public void testAtomicIntegerFieldUpdater() {
        AtomicIntegerFieldUpdater<User> updater = AtomicIntegerFieldUpdater.newUpdater(User.class, "age");
        User user = new User("1", "张三");

        Print.tco(updater.getAndIncrement(user));
        Print.tco(updater.getAndAdd(user, 100));

        Print.tco(updater.get(user));
    }

    /**
     * AtomicStampedReference解决ABA问题
     */
    @Test
    public void testAtomicStampedReference() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(2);
        AtomicStampedReference<Integer> stmpRef = new AtomicStampedReference<>(1, 0);

        ThreadUtil.getMixedTargetThreadPool().submit(() -> {
            boolean success = false;
            int stamp = stmpRef.getStamp();
            Print.tco("before sleep 500,value=" + stmpRef.getReference() + " stamp=" + stmpRef.getStamp());

            ThreadUtil.sleepMilliSeconds(500);
            success = stmpRef.compareAndSet(1, 10, stamp, stamp + 1);
            Print.tco("after sleep 500 cas 1,success=" + success + " value=" + stmpRef.getReference() + " stamp=" + stmpRef.getStamp());
            stamp++;
            success = stmpRef.compareAndSet(10, 1, stamp, stamp + 1);
            Print.tco("after sleep 500 cas 2,success=" + success + " value=" + stmpRef.getReference() + " stamp=" + stmpRef.getStamp());
            latch.countDown();
        });

        ThreadUtil.getMixedTargetThreadPool().submit(() -> {
            boolean success = false;
            int stamp = stmpRef.getStamp();
            Print.tco("before sleep 1000,value=" + stmpRef.getReference() + " stamp=" + stmpRef.getStamp());

            ThreadUtil.sleepMilliSeconds(1000);
            Print.tco("after sleep 1000 stamp=" + stmpRef.getStamp());

            success = stmpRef.compareAndSet(1, 20, stamp, stamp + 1);
            Print.tco("after cas 3,success=" + success + " value=" + stmpRef.getReference() + " stamp=" + stmpRef.getStamp());
            latch.countDown();
        });

        latch.await();
    }

    @Test
    public void testAtomicMarkableReference() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(2);
        AtomicMarkableReference<Integer> atomicRef = new AtomicMarkableReference<>(1, false);

        ThreadUtil.getMixedTargetThreadPool().submit(() -> {
            boolean success = false;
            Integer value = atomicRef.getReference();
            boolean mark = getMark(atomicRef);
            Print.tco("before sleep 500,value=" + value + " mark=" + mark);
            ThreadUtil.sleepMilliSeconds(500);
            success = atomicRef.compareAndSet(1, 10, mark, !mark);
            Print.tco("after sleep 500 cas 1,success=" + success + " value=" + atomicRef.getReference() + " mark=" + atomicRef.isMarked());

            latch.countDown();
        });

        ThreadUtil.getMixedTargetThreadPool().submit(() -> {
            boolean success = false;
            Integer value = atomicRef.getReference();
            boolean mark = getMark(atomicRef);
            Print.tco("before sleep 1000,value=" + value + " mark=" + mark);
            ThreadUtil.sleepMilliSeconds(1000);
            success = atomicRef.compareAndSet(1, 20, mark, !mark);
            Print.tco("after sleep 1000 cas 2,success=" + success + " value=" + atomicRef.getReference() + " mark=" + atomicRef.isMarked());
            latch.countDown();
        });

        latch.await();
    }


    private boolean getMark(AtomicMarkableReference<Integer> atomicRef) {
        boolean[] markHolder = {false};
        Integer value = atomicRef.get(markHolder);
        return markHolder[0];
    }

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(THREAD_COUNT);

        AtomicInteger atomicInteger = new AtomicInteger(0);

        for (int i = 0; i < THREAD_COUNT; i++) {
            ThreadUtil.getCpuIntenseTargetThreadPool().submit(() -> {
                for (int j = 0; j < 1000; j++) {
                    atomicInteger.getAndIncrement();
                }
                latch.countDown();
            });
        }
        latch.await();
        Print.tco("累加之和=" + atomicInteger.get());
    }
}
