package com.kim.locks;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * 可重入自旋
 */
public class ReentrantSpinLock implements Lock {

    private AtomicReference<Thread> owner = new AtomicReference<>();

    private int count = 0;

    @Override
    public void lock() {
        Thread thread = Thread.currentThread();
        if (thread == owner.get()) {
            ++count;
            return;
        }
        while (owner.compareAndSet(null, thread)) {
            Thread.yield();
        }
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public void unlock() {
        Thread thread = Thread.currentThread();
        if (thread == owner.get()) {
            if (count > 0) {
                --count;
            } else {
                owner.set(null);
            }
        }
    }

    @Override
    public Condition newCondition() {
        return null;
    }
}
