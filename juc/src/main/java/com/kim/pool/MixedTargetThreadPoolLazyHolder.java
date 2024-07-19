package com.kim.pool;

import com.kim.utils.ThreadUtil;

import java.util.concurrent.Callable;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static com.kim.utils.ThreadUtil.*;

public class MixedTargetThreadPoolLazyHolder {

    public static final int max = (null != System.getProperty(MIXED_THREAD_AMOUNT))
            ? Integer.parseInt(System.getProperty(MIXED_THREAD_AMOUNT))
            : MIXED_MAX;


    private static final ThreadPoolExecutor EXECUTOR = new ThreadPoolExecutor(
            max,
            max,
            KEEP_ALIVE_SECONDS,
            TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(QUEUE_SIZE),
            new ThreadUtil.CustomThreadFactory("mixed")
    );

    static {
        EXECUTOR.allowCoreThreadTimeOut(true);
        Runtime.getRuntime().addShutdownHook(
                new ShutdownHookThread("混合型任务线程池", new Callable<Void>() {
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
