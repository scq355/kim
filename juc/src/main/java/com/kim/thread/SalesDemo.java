package com.kim.thread;

import com.kim.utils.ThreadUtil;

import java.util.concurrent.atomic.AtomicInteger;

public class SalesDemo {
    public static final int MAX_AMOUNT = 5;

    static class StoreGoods extends Thread {
        StoreGoods(String name) {
            super(name);
        }

        private int goodsAmount = MAX_AMOUNT;

        @Override
        public void run() {
            for (int i = 0; i < MAX_AMOUNT; i++) {
                System.out.println(ThreadUtil.getCurThreadName() + " 卖出一件，还剩：" + (--goodsAmount));
                try {
                    sleep(10);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            System.out.println(ThreadUtil.getCurThreadName() + " 运行结束");
        }
    }

    static class MallGoods implements Runnable {
        private AtomicInteger goodsAmount = new AtomicInteger(MAX_AMOUNT);

        @Override
        public void run() {
            for (int i = 0; i <= MAX_AMOUNT; i++) {
                if (this.goodsAmount.get() > 0) {
                    System.out.println(ThreadUtil.getCurThreadName() + " 卖出一件，还剩：" + (goodsAmount.decrementAndGet()));
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            System.out.println(ThreadUtil.getCurThreadName() + " 运行结束.");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("商店版本销售");
        for (int i = 1; i <= 2; i++) {
            Thread thread = new StoreGoods("店员-" + i);
            thread.start();
        }

        Thread.sleep(1000);

        System.out.println("商场版本销售");
        MallGoods mallGoods = new MallGoods();
        for (int i = 1; i <= 2; i++) {
            Thread thread = new Thread(mallGoods, "商场销售员-" + i);
            thread.start();
        }

        System.out.println(ThreadUtil.getCurThreadName() + " 运行结束.");
    }
}
