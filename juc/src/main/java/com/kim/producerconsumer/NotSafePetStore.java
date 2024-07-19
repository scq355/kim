package com.kim.producerconsumer;

import com.kim.entity.Goods;
import com.kim.entity.IGoods;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NotSafePetStore {
    private static NotSafeDataBuffer<IGoods> notSafeDataBuffer = new NotSafeDataBuffer<>();

    static Callable<IGoods> produceAction = () -> {
        IGoods goods = Goods.productOne();
        notSafeDataBuffer.add(goods);
        return goods;
    };

    static Callable<IGoods> consumerAction = () -> notSafeDataBuffer.fetch();


    public static void main(String[] args) {
        System.setErr(System.out);

        final int THREAD_TOTAL = 20;

        ExecutorService threadPool = Executors.newFixedThreadPool(THREAD_TOTAL);
        for (int i = 0; i < 5; i++) {
            threadPool.submit(new Producer(produceAction, 500));
            threadPool.submit(new Consumer(consumerAction, 1500));
        }
    }
}
