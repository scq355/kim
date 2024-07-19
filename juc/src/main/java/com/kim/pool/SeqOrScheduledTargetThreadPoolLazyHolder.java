package com.kim.pool;

import com.kim.utils.ThreadUtil;

import java.util.concurrent.Callable;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * 懒汉式单例创建线程池：用于定时任务、顺序排队执行任务
 */
public class SeqOrScheduledTargetThreadPoolLazyHolder {

    static final ScheduledThreadPoolExecutor EXECUTOR = new ScheduledThreadPoolExecutor(1,
            new ThreadUtil.CustomThreadFactory("seq"));


    public static ScheduledThreadPoolExecutor getInnerExecutor() {
        return EXECUTOR;
    }

    static {
        System.out.println("线程池已初始化");

        Runtime.getRuntime().addShutdownHook(
                new ShutdownHookThread("定时和顺序任务线程池", new Callable<Void>() {
                    @Override
                    public Void call() throws Exception {
                        ThreadUtil.shutdownThreadPoolGracefully(EXECUTOR);
                        return null;
                    }
                })
        );
    }
}
