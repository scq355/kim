package com.kim.producerconsumer;

import com.kim.utils.Print;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class NotSafeDataBuffer<T> {
    public static final int MAX_AMOUNT = 10;

    private List<T> dataList = new LinkedList<>();

    private AtomicInteger amount = new AtomicInteger(0);

    public void add(T element) throws Exception {
        if (amount.get() > MAX_AMOUNT) {
            Print.tcfo("队列已经满了");
            return;
        }
        dataList.add(element);
        Print.tcfo(element + "");
        amount.incrementAndGet();

        if (amount.get() != dataList.size()) {
            throw new Exception(amount + "!=" + dataList.size());
        }
    }

    public T fetch() throws Exception {
        if (amount.get() <= 0) {
            Print.tcfo("队列已经空了");
            return null;
        }
        T element = dataList.remove(0);
        Print.tcfo(element + "");
        amount.decrementAndGet();
        if (amount.get() != dataList.size()) {
            throw new Exception(amount + "!=" + dataList.size());
        }
        return element;
     }
}
