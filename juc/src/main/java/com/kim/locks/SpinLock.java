package com.kim.locks;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * 不可重入自旋锁
 */
public class SpinLock implements Lock {

    private AtomicReference<Thread> owner = new AtomicReference<>();


    @Override
    public void lock() {
        Thread thread = Thread.currentThread();
        while (owner.compareAndSet(null, thread)) {
            // 让出当前剩余的CPU时间片
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
            owner.set(null);
        }
    }

    @Override
    public Condition newCondition() {
        return null;
    }
}
