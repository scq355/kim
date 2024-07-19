package com.kim.utils;

import com.kim.pool.CpuIntenseTargetThreadPoolLazyHolder;
import com.kim.pool.IoIntenseTargetThreadPoolLazyHolder;
import com.kim.pool.MixedTargetThreadPoolLazyHolder;
import com.kim.pool.SeqOrScheduledTargetThreadPoolLazyHolder;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.LockSupport;

public class ThreadUtil {

    public static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    public static final int IO_MAX = Math.max(2, CPU_COUNT * 2);

    public static final int KEEP_ALIVE_SECONDS = 30;

    public static final int QUEUE_SIZE = 128;

    public static final int MAXIMUM_POOL_SIZE = CPU_COUNT;


    public static ThreadPoolExecutor getMixedTargetThreadPool() {
        return MixedTargetThreadPoolLazyHolder.getInnerExecutor();
    }

    public static ThreadPoolExecutor getCpuIntenseTargetThreadPool() {
        return CpuIntenseTargetThreadPoolLazyHolder.getInnerExecutor();
    }

    public static ThreadPoolExecutor getIoIntenseTargetThreadPool() {
        return IoIntenseTargetThreadPoolLazyHolder.getInnerExecutor();
    }

    public static ScheduledThreadPoolExecutor getSeqOrScheduledExecutorService() {
        return SeqOrScheduledTargetThreadPoolLazyHolder.getInnerExecutor();
    }


    public static void sleepSeconds(int second) {
        LockSupport.parkNanos(second * 1000L * 1000L * 1000L);
    }


    public static void sleepMilliSeconds(int millisecond) {
        LockSupport.parkNanos(millisecond * 1000L * 1000L);
    }

    public static String getCurThreadName() {
        return Thread.currentThread().getName();
    }

    public static long getCurThreadId() {
        return Thread.currentThread().getId();
    }

    public static Thread getCurThread() {
        return Thread.currentThread();
    }

    /**
     * 最佳线程数 = ((线程等待时间+线程CPU时间) / 线程CPU时间) * CPU核数
     * 最佳线程数 = (线程等待时间与线程CPU时间之比 + 1) * CPU核数
     * 等待时间所占的比例越高，需要的线程就越多；CPU耗时所占的比例越高，需要的线程就越少
     * 多线程适用的场景一般是：存在相当比例非CPU耗时操作，如IO、网络操作，需要尽量提高并行化比率以提升CPU的利用率
     */

    public static final int MIXED_MAX = 128;
    public static final String MIXED_THREAD_AMOUNT = "mixed.thread.amount";


    public static class CustomThreadFactory implements ThreadFactory {

        private static final AtomicInteger poolNumber = new AtomicInteger(1);

        private final ThreadGroup group;

        private final AtomicInteger threadNumber = new AtomicInteger(1);

        private final String threadTag;

        public CustomThreadFactory(String threadTag) {
            SecurityManager manager = System.getSecurityManager();
            group = (manager != null) ? manager.getThreadGroup() :
                    Thread.currentThread().getThreadGroup();
            this.threadTag = "apppool-" + poolNumber.getAndIncrement()
                    + "-" + threadTag + "-";
        }

        @Override
        public Thread newThread(Runnable target) {
            Thread thread = new Thread(group, target, threadTag + threadNumber.getAndIncrement(), 0);
            if (thread.isDaemon()) {
                thread.setDaemon(false);
            }
            if (thread.getPriority() != Thread.NORM_PRIORITY) {
                thread.setPriority(Thread.NORM_PRIORITY);
            }
            return thread;
        }
    }

    public static void seqExecute(Runnable command) {
        getSeqOrScheduledExecutorService().execute(command);
    }

    public static void delayRun(Runnable command, int i, TimeUnit unit) {
        getSeqOrScheduledExecutorService().schedule(command, i, unit);
    }

    public static void scheduleAtFixedRate(Runnable command, int i, TimeUnit unit) {
        getSeqOrScheduledExecutorService().scheduleAtFixedRate(command, i, i, unit);
    }

    public static void shutdownThreadPoolGracefully(ExecutorService threadPool) {
        // 若已关闭则返回
        if (threadPool == null || threadPool.isTerminated()) {
            return;
        }
        try {
            // 拒绝接受新任务
            threadPool.shutdown();
        } catch (SecurityException | NullPointerException e) {
            return;
        }
        try {
            // 等60s，等待线程池中的任务完成执行
            if (!threadPool.awaitTermination(60, TimeUnit.SECONDS)) {
                // 取消正在执行的任务
                threadPool.shutdownNow();
                // 再次等待60s，若还未结束，可以再次尝试，或者直接放弃
                if (!threadPool.awaitTermination(60, TimeUnit.SECONDS)) {
                    System.err.println("线程池任务未正常执行结束");
                }
            }
        } catch (InterruptedException e) {
            threadPool.shutdownNow();
        }

        // 仍然没有关闭，循环关闭1k次，每次等待10ms
        if (!threadPool.isTerminated()) {
            try {
                for (int i = 0; i < 1000; i++) {
                    if (threadPool.awaitTermination(10, TimeUnit.MILLISECONDS)) {
                        break;
                    }
                    threadPool.shutdownNow();
                }
            } catch (Throwable e) {
                System.err.println(e.getMessage());
            }
        }
    }
}
