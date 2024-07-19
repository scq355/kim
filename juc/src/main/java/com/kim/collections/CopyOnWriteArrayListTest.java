package com.kim.collections;

import org.junit.Test;
import com.kim.utils.Print;
import com.kim.utils.ThreadUtil;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class CopyOnWriteArrayListTest {
    public static class ConcurrentTarget implements Runnable {

        List<String> targetList = null;

        public ConcurrentTarget(List<String> targetList) {
            this.targetList = targetList;
        }

        @Override
        public void run() {
            Iterator<String> iterator = targetList.iterator();
            while (iterator.hasNext()) {
                String threadName = Thread.currentThread().getName();
                Print.tco("开始往同步队列加入线程名称：" + threadName);
                targetList.add(threadName);
            }
        }
    }

    @Test
    public void testSynchronizedList() {
        List<String> notSafeList = Arrays.asList("a", "b", "c");

        List<String> synList = Collections.synchronizedList(notSafeList);

        ConcurrentTarget synchronizedListDemo = new ConcurrentTarget(synList);

        for (int i = 0; i < 10; i++) {
            new Thread(synchronizedListDemo, "线程" + i).start();
        }

        ThreadUtil.sleepSeconds(1000);
    }

    @Test
    public void testCopyOnWriteArrayList() {
        List<String> notSafeList = Arrays.asList("a", "b", "c");
        List<String> copyOnWriteArrayList = new CopyOnWriteArrayList<>(notSafeList);

        ConcurrentTarget copyOnWriteArrayListDemo = new ConcurrentTarget(copyOnWriteArrayList);

        for (int i = 0; i < 10; i++) {
            new Thread(copyOnWriteArrayListDemo, "线程" + i).start();
        }

        ThreadUtil.sleepSeconds(1000);
    }
}
