package com.kim.asynccallback;

import com.kim.utils.Print;
import com.kim.utils.ThreadUtil;


public class JoinDemo {
    public static final int SLEEP_GAP = 500;
    public static String getCurThreadName() {
        return ThreadUtil.getCurThread().getName();
    }

    static class HotWaterThread extends Thread {
        public HotWaterThread() {
            super("** 烧水-Thread");
        }

        @Override
        public void run() {
            try {
                Print.tcfo("洗好水壶");
                Print.tcfo("灌上凉水");
                Print.tcfo("放在火上");
                Thread.sleep(SLEEP_GAP);
                Print.tcfo("水开了");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            Print.tcfo(getCurThreadName() + " 运行结束.");
        }
    }

    static class WashThread extends Thread {
        public WashThread() {
            super("$$ 清洗-Thread");
        }

        @Override
        public void run() {
            try {
                Print.tcfo("洗茶壶");
                Print.tcfo("洗茶杯");
                Print.tcfo("拿茶叶");
                Thread.sleep(SLEEP_GAP);
                Print.tcfo("洗完了");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            Print.tcfo(getCurThreadName() + " 运行结束.");
        }
    }

    public static void main(String[] args) {
        Thread hThread = new HotWaterThread();
        Thread wThread = new WashThread();

        hThread.start();
        wThread.start();

        try {
            hThread.join();
            wThread.join();

            Thread.currentThread().setName("主线程");
            Print.tcfo("泡茶喝");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Print.tcfo(getCurThreadName() + " 运行结束");
    }
}
