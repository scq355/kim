package com.kim.pool;

import java.util.concurrent.Callable;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static com.kim.utils.ThreadUtil.*;

/**
 * 使用懒汉式单例模式创建线程池，如果代码没有用到此线程池，就不会立即创建。
 */
public class IoIntenseTargetThreadPoolLazyHolder {

    /**
     * 使得在接收到新任务时，如果没有空闲工作线程，就优先创建新的线程去执行新任务，
     * 而不是优先加入阻塞队列，等待现有工作线程空闲后再执行
     */
    public static final ThreadPoolExecutor EXECUTOR = new ThreadPoolExecutor(IO_MAX,
            IO_MAX,
            KEEP_ALIVE_SECONDS,
            TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(QUEUE_SIZE),
            new CustomThreadFactory("io"));

    static {
        /**
         * keepAliveTime参数所设置的Idle超时策略也将被应用于核心线程，
         * 当池中的线程长时间空闲时，可以自行销毁
         */
        EXECUTOR.allowCoreThreadTimeOut(true);
        Runtime.getRuntime().addShutdownHook(
                new ShutdownHookThread("IO密集型任务线程池", new Callable<Void>() {
                    @Override
                    public Void call() throws Exception {
                        shutdownThreadPoolGracefully(EXECUTOR);
                        return null;
                    }
                })
        );
    }

    public static ThreadPoolExecutor getInnerExecutor() {
        return EXECUTOR;
    }
}
