package com.kim.asynccallback;

import com.kim.utils.Print;
import com.kim.utils.ThreadUtil;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class FutureDemo {
    public static final int SLEEP_GAP = 500;
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

    public static void drinkTea(boolean waterOk, boolean cupOk) {
        if (waterOk && cupOk) {
            Print.tcfo("泡茶喝");
        } else if (!waterOk) {
            Print.tcfo("烧水失败，没有茶喝");
        } else if (!cupOk) {
            Print.tcfo("杯子洗不了，没有茶喝");
        }
    }

    public static void main(String[] args) {
        Thread.currentThread().setName("主线程");
        Callable<Boolean> hotWaterJob = new HotWaterJob();
        FutureTask<Boolean> hTask = new FutureTask<>(hotWaterJob);
        Thread hThread = new Thread(hTask, "** 烧水-thread");

        Callable<Boolean> washJob = new WashJob();
        FutureTask<Boolean> wTask = new FutureTask<>(washJob);
        Thread wThread = new Thread(wTask, "$$ 清洗-thread");

        hThread.start();
        wThread.start();

        try {
            Boolean waterOk = hTask.get();
            Boolean cupOk = wTask.get();

            drinkTea(waterOk, cupOk);
        } catch (InterruptedException e) {
            Print.tcfo(getCurThreadName() + " 发生异常被中断.");
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }

        Print.tcfo(getCurThreadName() + " 运行结束.");
    }
}
