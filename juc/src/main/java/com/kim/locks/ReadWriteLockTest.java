package com.kim.locks;

import com.kim.utils.DateUtil;
import com.kim.utils.Print;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockTest {

    static final Map<String, String> MAP = new HashMap<>();

    static final ReentrantReadWriteLock LOCK = new ReentrantReadWriteLock();

    static final Lock READ_LOCK = LOCK.readLock();

    static final Lock WRITE_LOCK = LOCK.writeLock();

    public static Object put(String key, String value) {
        WRITE_LOCK.lock();
        try {
            Print.tco(DateUtil.getNow() + " 抢占了WRITE_LOCK，开始写操作");
            Thread.sleep(10 * 1000);
            return MAP.put(key, value);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            WRITE_LOCK.unlock();
        }
        return null;
    }

    public static Object get(String key) {
        READ_LOCK.lock();
        try {
            Print.tco(DateUtil.getNow() + " 抢占了READ_LOCK，开始读操作");
            Thread.sleep(10 * 1000);
            return MAP.get(key);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            READ_LOCK.unlock();
        }
        return null;
    }


    public static void main(String[] args) {
        Runnable writeTarget = () -> put("key", "value");
        Runnable readTarget = () -> get("key");

        for (int i = 0; i < 2; i++) {
            new Thread(writeTarget, "写线程" + i).start();
        }
        for (int i = 0; i < 4; i++) {
            new Thread(readTarget, "读线程" + i).start();
        }
    }
}
