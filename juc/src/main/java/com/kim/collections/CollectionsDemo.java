package com.kim.collections;

import com.kim.utils.Print;
import com.kim.utils.ThreadUtil;

import java.util.Collection;
import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.CountDownLatch;

public class CollectionsDemo {
    public static void main(String[] args) throws InterruptedException {
        SortedSet<String> elementSet = new TreeSet<>();

        elementSet.add("elem 1");
        elementSet.add("elem 2");

        Collection<String> sorSet = Collections.synchronizedCollection(elementSet);
        System.out.println("sorSet is = " + sorSet);
        CountDownLatch latch = new CountDownLatch(5);
        for (int i = 0; i < 5; i++) {
            int finalI = i;
            ThreadUtil.getCpuIntenseTargetThreadPool()
                    .submit(() -> {
                        sorSet.add("element " + (3 + finalI));
                        Print.tco("element " + (3 + finalI));
                        latch.countDown();
                    });
        }
        latch.await();
        System.out.println("sortedSet is:" + sorSet);
    }
}
