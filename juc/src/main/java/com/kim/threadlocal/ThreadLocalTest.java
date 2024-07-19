package com.kim.threadlocal;

import lombok.Data;
import com.kim.utils.ThreadUtil;

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadLocalTest {

    @Data
    static class Foo {
        static final AtomicInteger AMOUNT = new AtomicInteger(0);
        int index = 0;
        int bar = 10;

        public Foo() {
            index = AMOUNT.decrementAndGet();
        }

        public String toString() {
            return index + " @Foo{bar=" + bar + '}';
        }
    }

    private static final ThreadLocal<Foo> LOCAL_FOO = new ThreadLocal<>();

    public static void main(String[] args) {
        ThreadPoolExecutor threadPool = ThreadUtil.getMixedTargetThreadPool();
        for (int i = 0; i < 5; i++) {
            threadPool.execute(new Runnable() {
                @Override
                public void run() {
                    if (LOCAL_FOO.get() == null) {
                        LOCAL_FOO.set(new Foo());
                    }
                    System.out.println("初始的本地值：" + LOCAL_FOO.get());
                    for (int j = 0; j < 10; j++) {
                        Foo foo = LOCAL_FOO.get();
                        foo.setBar(foo.getBar() + 1);
                        ThreadUtil.sleepMilliSeconds(10);
                    }
                    System.out.println("累加10次后的本地值：" + LOCAL_FOO.get());
                    LOCAL_FOO.remove();
                }
            });
        }
    }
}
