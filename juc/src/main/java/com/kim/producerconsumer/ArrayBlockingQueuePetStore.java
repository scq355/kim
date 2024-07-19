package com.kim.producerconsumer;

import com.kim.entity.Goods;
import com.kim.entity.IGoods;
import com.kim.utils.JvmUtil;
import com.kim.utils.Print;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ArrayBlockingQueuePetStore {
    public static final int MAX_AMOUNT = 10;

    static class DataBuffer<T> {
        private ArrayBlockingQueue<T> dataList = new ArrayBlockingQueue<>(MAX_AMOUNT);

        public void add(T element) {
            dataList.add(element);
        }

        public T fetch() throws InterruptedException {
            return dataList.take();
        }
    }


    public static void main(String[] args) {
        Print.tcfo("当前进程ID=" + JvmUtil.getProcessID());
        System.setErr(System.out);

        DataBuffer<IGoods> dataBuffer = new DataBuffer<>();

        Callable<IGoods> produceAction = () -> {
            IGoods goods = Goods.productOne();
            dataBuffer.add(goods);
            return goods;
        };

        Callable<IGoods> consumerAction = () -> dataBuffer.fetch();

        final int THREAD_TOTAL = 20;
        ExecutorService threadPool = Executors.newFixedThreadPool(THREAD_TOTAL);

        final int CONSUMER_TOTAL = 11;
        final int PRODUCER_TOTAL = 1;

        for (int i = 0; i < PRODUCER_TOTAL; i++) {
            threadPool.submit(new Producer(produceAction, 50));
        }

        for (int i = 0; i < CONSUMER_TOTAL; i++) {
            threadPool.submit(new Consumer(consumerAction, 100));
        }
    }

}
