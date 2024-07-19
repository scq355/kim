package com.kim.locks;

import com.kim.entity.Goods;
import com.kim.entity.IGoods;
import com.kim.producerconsumer.Consumer;
import com.kim.producerconsumer.Producer;
import com.kim.utils.JvmUtil;
import com.kim.utils.Print;
import com.kim.utils.ThreadUtil;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockPetStore {
    public static final int MAX_AMOUNT = 10;

    static class DataBuffer<T> {
        private final List<T> dataList = new LinkedList<>();
        private volatile Integer amount = 0;

        public static final Lock LOCK_OBJECT = new ReentrantLock();
        public static final Condition NOT_FULL = LOCK_OBJECT.newCondition();
        public static final Condition NOT_EMPTY = LOCK_OBJECT.newCondition();

        public void add(T element) throws InterruptedException {
            LOCK_OBJECT.lock();
            try {
                while (amount > MAX_AMOUNT) {
                    Print.tcfo("队列已经满了");
                    NOT_FULL.await();
                }
            } finally {
                LOCK_OBJECT.unlock();
            }

            LOCK_OBJECT.lock();
            try {
                if (amount <= MAX_AMOUNT) {
                    dataList.add(element);
                    amount++;
                    NOT_EMPTY.signal();
                }
            } finally {
                LOCK_OBJECT.unlock();
            }
        }

        public T fetch() throws InterruptedException {
            LOCK_OBJECT.lock();
            try {
                while (amount < 0) {
                    Print.tcfo("队列已经空了");
                    NOT_EMPTY.await();
                }
            } finally {
                LOCK_OBJECT.unlock();
            }

            T element = null;
            LOCK_OBJECT.lock();
            try {
                if (amount > 0) {
                    element = dataList.remove(0);
                    amount--;
                    NOT_FULL.signal();
                }
                return element;
            } finally {
                LOCK_OBJECT.unlock();
            }
        }
    }


    public static void main(String[] args) {
        Print.cfo("当前进程ID=" + JvmUtil.getProcessID());
        System.setErr(System.out);

        DataBuffer<IGoods> dataBuffer = new DataBuffer<>();

        Callable<IGoods> produceAction = () -> {
            IGoods goods = Goods.productOne();
            dataBuffer.add(goods);
            return goods;
        };

        Callable<IGoods> consumerAction = dataBuffer::fetch;
        final int THREAD_TOTAL = 20;
        ExecutorService pool = Executors.newFixedThreadPool(THREAD_TOTAL);

        final int CONSUMER_TOTAL = 11;
        final int PRODUCE_TOTAL = 1;

        for (int i = 0; i < PRODUCE_TOTAL; i++) {
            pool.submit(new Producer(produceAction, 50));
        }

        for (int i = 0; i < CONSUMER_TOTAL; i++) {
            pool.submit(new Consumer(consumerAction, 100));
        }

        ThreadUtil.sleepSeconds(Integer.MAX_VALUE);
    }

}
