package com.kim.locks;

import com.kim.utils.DateUtil;
import com.kim.utils.Print;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockTest2 {

    static final Map<String, String> MAP = new HashMap<>();

    static final ReentrantReadWriteLock LOCK = new ReentrantReadWriteLock();

    static final Lock READ_LOCK = LOCK.readLock();

    static final Lock WRITE_LOCK = LOCK.writeLock();

    public static Object put(String key, String value) {
        WRITE_LOCK.lock();
        try {
            Print.tco(DateUtil.getNow() + " 抢占了WRITE_LOCK，开始写操作");
            Thread.sleep(3 * 1000);
            String put = MAP.put(key, value);
            Print.tco("尝试降级写锁为读锁");
            READ_LOCK.lock();
            Print.tco("写锁降级为读锁成功");
            return put;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            READ_LOCK.unlock();
            WRITE_LOCK.unlock();
        }
        return null;
    }

    public static Object get(String key) {
        READ_LOCK.lock();
        try {
            Print.tco(DateUtil.getNow() + " 抢占了READ_LOCK，开始读操作");
            Thread.sleep(3 * 1000);
            String value = MAP.get(key);
            Print.tco("尝试升级读锁为写锁");
            WRITE_LOCK.lock();
            Print.tco("读锁升级为写锁成功");
            return value;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            WRITE_LOCK.unlock();
            READ_LOCK.unlock();
        }
        return null;
    }


    public static void main(String[] args) {
        Runnable writeTarget = () -> put("key", "value");
        Runnable readTarget = () -> get("key");

        new Thread(writeTarget, "写线程").start();
        new Thread(readTarget, "读线程").start();
    }
}
