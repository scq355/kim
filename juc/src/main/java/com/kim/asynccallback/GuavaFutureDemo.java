package com.kim.asynccallback;

import com.google.common.util.concurrent.*;
import com.kim.utils.Print;
import com.kim.utils.ThreadUtil;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GuavaFutureDemo {
    public static final int SLEEP_GAP = 3000;

    public static String getCurThreadName() {
        return ThreadUtil.getCurThread().getName();
    }

    static class HotWaterJob implements Callable<Boolean> {
        @Override
        public Boolean call() throws Exception {
            try {
                Print.tcfo("洗好水壶");
                Print.tcfo("灌上凉水");
                Print.tcfo("放在火上");
                Thread.sleep(SLEEP_GAP);
                Print.tcfo("水开了");
            } catch (InterruptedException e) {
                Print.tcfo( getCurThreadName() + "发生异常被中断");
                return false;
            }
            Print.tcfo(getCurThreadName() + " 运行结束.");
            return true;
        }
    }

    static class WashJob implements Callable<Boolean> {

        @Override
        public Boolean call() throws Exception {
            try {
                Print.tcfo("洗茶壶");
                Print.tcfo("洗茶杯");
                Print.tcfo("拿茶叶");
                Thread.sleep(SLEEP_GAP);
                Print.tcfo("洗完了");
            } catch (InterruptedException e) {
                Print.tcfo( getCurThreadName() + "发生异常被中断");
                return false;
            }
            Print.tcfo(getCurThreadName() + " 运行结束.");
            return true;
        }
    }

    static class DrinkJob {
        boolean waterOk = false;
        boolean cupOk = false;

        public void drinkTea() {
            if (waterOk && cupOk) {
                Print.tcfo("泡茶喝，茶喝完");
                this.waterOk = false;
            }
        }
    }

    public static void main(String[] args) {
        Thread.currentThread().setName("泡茶喝线程");

        DrinkJob drinkJob = new DrinkJob();

        Callable<Boolean> hotJob = new HotWaterJob();
        Callable<Boolean> washJob = new WashJob();

        ExecutorService jPool = Executors.newFixedThreadPool(10);
        ListeningExecutorService gPool = MoreExecutors.listeningDecorator(jPool);

        FutureCallback<Boolean> hotWaterHook = new FutureCallback<Boolean>() {

            @Override
            public void onSuccess(Boolean r) {
                if (r) {
                    drinkJob.waterOk = true;
                    drinkJob.drinkTea();
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                Print.tcfo("烧水失败，没有茶喝");
            }
        };

        ListenableFuture<Boolean> hotFuture = gPool.submit(hotJob);
        Futures.addCallback(hotFuture, hotWaterHook, MoreExecutors.directExecutor());

        ListenableFuture<Boolean> washFuture = gPool.submit(washJob);
        Futures.addCallback(washFuture, new FutureCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean r) {
                if (r) {
                    drinkJob.cupOk = true;
                    drinkJob.drinkTea();
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                Print.tcfo("洗杯子失败，没有茶喝");
            }
        }, MoreExecutors.directExecutor());

        Print.tcfo(getCurThreadName() + "干点其他事情...");
        ThreadUtil.sleepSeconds(1);
        Print.tcfo(getCurThreadName() + "执行完成");

    }
}
