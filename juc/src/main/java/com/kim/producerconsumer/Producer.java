package com.kim.producerconsumer;

import com.kim.utils.Print;
import com.kim.utils.ThreadUtil;

import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;

public class Producer implements Runnable{

    // 生产的时间间隔
    public static final int PRODUCE_GAP = 200;

    // 总次数
    static final AtomicInteger TURN = new AtomicInteger(0);

    // 生产者对象编号
    static final AtomicInteger PRODUCER_NO = new AtomicInteger(1);

    String name = null;

    Callable action = null;

    int gap = PRODUCE_GAP;

    public Producer(Callable action, int gap) {
        this.action = action;
        this.gap = gap;
        name = "生产者-" + PRODUCER_NO.incrementAndGet();
    }

    @Override
    public void run() {
        while (true) {
            try {
                // 执行生产动作
                Object out = action.call();
                if (null != out) {
                    Print.tcfo("第" + TURN.get() + "轮次生产：" + out);
                }
                ThreadUtil.sleepMilliSeconds(gap);
                // 增加生产轮次
                TURN.incrementAndGet();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
