package com.kim.pool;

import com.kim.utils.ThreadUtil;

import java.util.concurrent.Callable;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static com.kim.utils.ThreadUtil.*;

public class CpuIntenseTargetThreadPoolLazyHolder {
    private static final ThreadPoolExecutor EXECUTOR = new ThreadPoolExecutor(
            MAXIMUM_POOL_SIZE,
            MAXIMUM_POOL_SIZE,
            KEEP_ALIVE_SECONDS,
            TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(QUEUE_SIZE),
            new ThreadUtil.CustomThreadFactory("cpu")
    );

    static {
        EXECUTOR.allowCoreThreadTimeOut(true);
        Runtime.getRuntime().addShutdownHook(
                new ShutdownHookThread("CPU密集型任务线程池", new Callable<Void>() {
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
