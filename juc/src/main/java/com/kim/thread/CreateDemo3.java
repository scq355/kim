package com.kim.thread;

import com.kim.utils.ThreadUtil;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class CreateDemo3 {
    public static final int MAX_TIMES = 5;
    public static final int COMPUTE_TIMES = 100000000;

    static class ReturnableTask implements Callable<Long> {
        @Override
        public Long call() throws Exception {
            long startTime = System.currentTimeMillis();
            System.out.println(ThreadUtil.getCurThreadName() + " 线程运行开始.");
            Thread.sleep(1000);

            for (int i = 0; i < COMPUTE_TIMES; i++) {
                int j = i * 10000;
            }
            long used = System.currentTimeMillis() - startTime;
            System.out.println(ThreadUtil.getCurThreadName() + " 线程运行结束.");
            return used;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ReturnableTask task = new ReturnableTask();
        FutureTask<Long> futureTask = new FutureTask<>(task);

        Thread thread = new Thread(futureTask, "returnableTask");
        thread.start();
        Thread.sleep(500);
        System.out.println(ThreadUtil.getCurThreadName() + " 让子弹飞一会儿");
        for (int i = 0; i < COMPUTE_TIMES / 2; i++) {
            int j = i * 10000;
        }
        System.out.println(ThreadUtil.getCurThreadName() + " 获取并发任务的执行结果");
        try {
            System.out.println(thread.getName() + " 线程占用时间：" + futureTask.get());
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
        System.out.println(ThreadUtil.getCurThreadName() + " 运行结束.");
    }
}
