package com.kim.locks;

import com.kim.utils.DateUtil;
import com.kim.utils.Print;
import com.kim.utils.ThreadUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.StampedLock;

public class StampedLockTest {

    static final Map<String, String> MAP = new HashMap<>();

    static final StampedLock STAMPED_LOCK = new StampedLock();

    public static Object put(String key, String value) {
        long stamp = STAMPED_LOCK.writeLock();
        try {
            Print.tco(DateUtil.getNow() + "抢占写锁，开始写");
            Thread.sleep(3 * 1000);
            return MAP.put(key, value);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            Print.tco(DateUtil.getNow() + "释放写锁");
            STAMPED_LOCK.unlockWrite(stamp);
        }
        return null;
    }

    public static Object pessimisticRead(String key) {
        Print.tco(DateUtil.getNow() + "LOCK进入过写模式，只能悲观读");
        long stamp = STAMPED_LOCK.readLock();
        try {
            Print.tco(DateUtil.getNow() + "抢占读锁");
            return MAP.get(key);
        } finally {
            Print.tco(DateUtil.getNow() + "释放读锁");
            STAMPED_LOCK.unlockRead(stamp);
        }
    }

    public static Object optimisticRead(String key) {
        String value = null;
        long stamp = STAMPED_LOCK.tryOptimisticRead();
        if (stamp != 0) {
            Print.tco(DateUtil.getNow() + "乐观读的印戳获取成功");
            ThreadUtil.sleepSeconds(1);
            value = MAP.get(key);
        } else {
            // stamp == 0:当前锁为写锁模式
            Print.tco(DateUtil.getNow() + "乐观读的印戳获取失败");
            return pessimisticRead(key);
        }
        // 判断lock是否进入过写模式
        if (!STAMPED_LOCK.validate(stamp)) {
            Print.tco(DateUtil.getNow() + "乐观读的印戳已过期");
            return pessimisticRead(key);
        } else {
            // 乐观读的印戳有效，写锁未被占用过，不用加悲观读锁直接读，减少读锁开销
            Print.tco(DateUtil.getNow() + "乐观读的印戳未过期");
            return value;
        }
    }


    public static void main(String[] args) {
        Runnable writeTarget = () -> put("key", "value");
        Runnable readTarget = () -> optimisticRead("key");

        new Thread(writeTarget, "写线程").start();
        new Thread(readTarget, "读线程").start();
    }
}
