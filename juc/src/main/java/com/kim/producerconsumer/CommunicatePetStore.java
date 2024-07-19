package com.kim.producerconsumer;

import com.kim.entity.Goods;
import com.kim.entity.IGoods;
import com.kim.utils.JvmUtil;
import com.kim.utils.Print;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CommunicatePetStore {

    public static final int MAX_AMOUNT = 10;

    static class DataBuffer<T> {
        private List<T> dataList = new LinkedList<>();
        private Integer amount = 0;

        private final Object LOCK_OBJECT = new Object();
        private final Object NOT_FULL = new Object();
        private final Object NOT_EMPTY = new Object();

        public void add(T element) throws InterruptedException {
            while (amount > MAX_AMOUNT) {
                synchronized (NOT_FULL) {
                    Print.tcfo("队列已满了");
                    NOT_FULL.wait();
                }
            }
            synchronized (LOCK_OBJECT) {
                if (amount < MAX_AMOUNT) { // 加上双重检查，模拟双检锁在单例模式中应用
                    dataList.add(element);
                    amount++;
                }
            }
            synchronized (NOT_EMPTY) {
                NOT_EMPTY.notify();
            }
        }

        public T fetch() throws InterruptedException {
            while (amount <= 0) {
                synchronized (NOT_EMPTY) {
                    Print.tcfo("队列已经空了");
                    NOT_EMPTY.wait();
                }
            }
            T element = null;
            synchronized (LOCK_OBJECT) {
                if (amount > 0) {  // 加上双重检查，模拟双检锁在单例模式中应用
                    element = dataList.remove(0);
                    amount--;
                }
            }
            synchronized (NOT_FULL) {
                NOT_FULL.notify();
            }
            return element;
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

        ExecutorService threadPool = Executors.newFixedThreadPool(THREAD_TOTAL);

        final int CONSUMER_TOTAL = 11;
        final int PRODUCE_TOTAL = 1;

        for (int i = 0; i < PRODUCE_TOTAL; i++) {
            threadPool.submit(new Producer(produceAction, 50));
        }

        for (int i = 0; i < CONSUMER_TOTAL; i++) {
            threadPool.submit(new Consumer(consumerAction, 100));
        }

    }


}
