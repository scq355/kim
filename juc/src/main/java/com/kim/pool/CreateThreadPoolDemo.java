package com.kim.pool;

import org.junit.Test;
import com.kim.utils.ThreadUtil;

import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class CreateThreadPoolDemo {
    public static final int SLEEP_GAP = 500;

    static class TargetTask implements Runnable {

        static AtomicInteger taskNo = new AtomicInteger(1);
        protected String taskName;

        public TargetTask() {
            taskName = "task-" + taskNo.get();
            taskNo.incrementAndGet();
        }

        @Override
        public void run() {
            System.out.println("任务：" + taskName + " doing");
            ThreadUtil.sleepMilliSeconds(SLEEP_GAP);
            System.out.println(taskName + "运行结束.");
        }
    }

    @Test
    public void testSingleThreadExecutor() throws InterruptedException {
        ExecutorService pool = Executors.newSingleThreadExecutor();
        for (int i = 0; i < 5; i++) {
            pool.execute(new TargetTask());
            pool.submit(new TargetTask());
        }

        Thread.sleep(1000 * 1000L);
        pool.shutdown();
    }

    @Test
    public void testNewFixedThreadPool() throws InterruptedException {
        ExecutorService pool = Executors.newFixedThreadPool(3);
        for (int i = 0; i < 5; i++) {
            pool.execute(new TargetTask());
            pool.submit(new TargetTask());
        }
        Thread.sleep(1000 * 1000L);
        pool.shutdown();
    }


    @Test
    public void testNewCachedThreadPool() throws InterruptedException {
        ExecutorService pool = Executors.newCachedThreadPool();
        for (int i = 0; i < 5; i++) {
            pool.execute(new TargetTask());
            pool.submit(new TargetTask());
        }
        Thread.sleep(1000 * 1000L);
        pool.shutdown();
    }


    @Test
    public void testNewScheduledThreadPool() throws InterruptedException {
        ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(2);
        for (int i = 0; i < 5; i++) {
            scheduledThreadPool.scheduleAtFixedRate(new TargetTask(), 0, 5, TimeUnit.SECONDS);
        }
        Thread.sleep(1000 * 1000L);
        scheduledThreadPool.shutdown();
    }


    @Test
    public void testSubmit() throws InterruptedException {
        ScheduledExecutorService pool = Executors.newScheduledThreadPool(2);
        Future<Integer> future = pool.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return new Random().nextInt(300);
            }
        });

        try {
            Integer result = future.get();
            System.out.println("异步执行的结果是：" + result);
        } catch (ExecutionException e) {
            System.out.println("异步调用被中断");
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            System.out.println("异步调用过程中，发生了异常");
            throw new RuntimeException(e);
        }

        Thread.sleep(10 * 1000L);
        pool.shutdown();
    }


    static class TargetTaskWithError extends TargetTask {
        @Override
        public void run() {
            super.run();
            throw new RuntimeException("Error from " + taskName);
        }
    }

    @Test
    public void testSubmit2() throws InterruptedException {
        ScheduledExecutorService pool = Executors.newScheduledThreadPool(2);
        pool.execute(new TargetTaskWithError());

        Future<?> future = pool.submit(new TargetTaskWithError());
        try {
            if (future.get() == null) {
                System.out.println("任务完成");
            }
        } catch (Exception e) {
            System.out.println(e.getCause().getMessage());
        }
        Thread.sleep(10 * 1000L);
        pool.shutdown();
    }

    @Test
    public void testThreadPoolExecutor() throws InterruptedException {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 100, 100, TimeUnit.SECONDS, new LinkedBlockingQueue<>(100));
        for (int i = 0; i < 5; i++) {
            final int taskIndex = i;
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println("taskIndex=" + taskIndex);
                    try {
                        Thread.sleep(Long.MAX_VALUE);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });

        }
        while (true) {
            System.out.println(ThreadUtil.getCurThreadName() + "- activeCount:" + executor.getActiveCount() + " - taskCount:" + executor.getTaskCount());
            Thread.sleep(1000L);
        }
    }


    public static class SimpleThreadFactory implements ThreadFactory {

        static AtomicInteger threadNo = new AtomicInteger(1);

        @Override
        public Thread newThread(Runnable target) {
            String threadName = "simpleThread-" + threadNo.get();
            System.out.println("创建一个线程，名称为：" + threadName);
            threadNo.incrementAndGet();
            Thread thread = new Thread(target, threadName);
            thread.setDaemon(true);
            return thread;
        }
    }

    @Test
    public void testThreadFactory() throws InterruptedException {
        ExecutorService pool = Executors.newFixedThreadPool(2, new SimpleThreadFactory());
        for (int i = 0; i < 5; i++) {
            pool.submit(new TargetTask());
        }

        Thread.sleep(10 * 1000L);
        System.out.println("关闭线程池");
        pool.shutdown();
    }

    @Test
    public void testHooks() throws InterruptedException {
        ThreadPoolExecutor pool = new ThreadPoolExecutor(2, 4, 60, TimeUnit.SECONDS, new LinkedBlockingQueue<>(2)) {

            final ThreadLocal<Long> startTime = new ThreadLocal<>();

            @Override
            protected void terminated() {
                System.out.println("调度器已经终止");
            }

            @Override
            protected void beforeExecute(Thread t, Runnable target) {
                System.out.println(ThreadUtil.getCurThreadName() + "前钩被执行");
                startTime.set(System.currentTimeMillis());
                super.beforeExecute(t, target);
            }

            @Override
            protected void afterExecute(Runnable target, Throwable t) {
                super.afterExecute(target, t);
                long time = System.currentTimeMillis() - startTime.get();
                System.out.println(ThreadUtil.getCurThreadName() + " 后钩被执行，任务执行时长（ms）:" + time);
                startTime.remove();
            }
        };

        for (int i = 1; i < 5; i++) {
            pool.execute(new TargetTask());
        }

        Thread.sleep(10 * 1000L);
        System.out.println("关闭线程池");
        pool.shutdown();
    }

    public static class CustomIgnorePolicy implements RejectedExecutionHandler {
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            System.out.println(ThreadUtil.getCurThreadName() + " rejected; " + "- getTaskCount " + executor.getTaskCount());
        }
    }

    @Test
    public void testCustomIgnorePolicy() throws InterruptedException {
        int corePoolSize = 2;
        int maximumPoolSize = 4;
        long keepAliveTime = 10;
        TimeUnit unit = TimeUnit.SECONDS;

        ArrayBlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(2);
        SimpleThreadFactory threadFactory = new SimpleThreadFactory();
        RejectedExecutionHandler policy = new CustomIgnorePolicy();

        ThreadPoolExecutor pool = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, policy);
        pool.prestartAllCoreThreads();
        for (int i = 0; i <= 10; i++) {
            pool.execute(new TargetTask());
        }
        Thread.sleep(10 * 1000L);
        System.out.println("关闭线程池");
        pool.shutdown();
    }

    @Test
    public void testNewFixedThreadPool2() {
        // 原始的可以动态调整
        ExecutorService fixedExecutorService = Executors.newFixedThreadPool(1);
        ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) fixedExecutorService;
        System.out.println(threadPoolExecutor.getMaximumPoolSize());
        threadPoolExecutor.setCorePoolSize(8);
        // single的不可以
        ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
        ((ThreadPoolExecutor)singleThreadExecutor).setCorePoolSize(8);
    }

    @Test
    public void testMixedThreadPool() throws InterruptedException {
        System.getProperties().put("mixed.thread", 600);

        ExecutorService pool = ThreadUtil.getMixedTargetThreadPool();

        for (int i = 0; i < 1000; i++) {
            try {
                ThreadUtil.sleepMilliSeconds(10);
                pool.submit(new TargetTask());
            } catch (RejectedExecutionException e) {
                e.printStackTrace();
            }
        }

        Thread.sleep(10 * 1000L);
        System.out.println("关闭线程池");
    }

}
