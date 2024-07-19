package com.kim.thread;


import com.kim.utils.ThreadUtil;

import java.util.concurrent.*;

public class CreateDemo4 {
    public static final int MAX_TURN = 5;
    public static final int COMPUTE_TIMES = 100000000;

    private static ExecutorService pool = Executors.newFixedThreadPool(3);

    static class DemoThread implements Runnable {

        @Override
        public void run() {
            for (int i = 1; i < MAX_TURN; i++) {
                System.out.println(ThreadUtil.getCurThreadName() + ", 轮次：" + i);
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    static class ReturnableTask implements Callable<Long> {

        @Override
        public Long call() throws Exception {
            long startTime = System.currentTimeMillis();
            System.out.println(ThreadUtil.getCurThreadName() + " 线程开始运行.");
            for (int i = 1; i < MAX_TURN; i++) {
                System.out.println(ThreadUtil.getCurThreadName() + ", 轮次:" + i);
                Thread.sleep(10);
            }
            long used = System.currentTimeMillis() - startTime;
            System.out.println(ThreadUtil.getCurThreadName() + " 现成运行结束.");
            return used;
        }
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        pool.execute(new DemoThread());
        pool.execute(new Runnable() {
            @Override
            public void run() {
                for (int i = 1; i < MAX_TURN; i++) {
                    System.out.println(ThreadUtil.getCurThreadName() + " ,轮次：" + i);
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
        Future<Long> future = pool.submit(new ReturnableTask());
        Long result = future.get();
        System.out.println("异步任务的执行结果为：" + result);
        Thread.sleep(Integer.MAX_VALUE);
    }
}
