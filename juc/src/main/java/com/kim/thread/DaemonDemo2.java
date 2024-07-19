package com.kim.thread;

import com.kim.utils.ThreadUtil;

public class DaemonDemo2 {

    public static final int SLEEP_GAP = 500;
    public static final int MAX_TURN = 4;

    static class NormalThread extends Thread {
        static int threadNo = 1;
        public NormalThread() {
            super("normalThread-" + threadNo);
            threadNo++;
        }

        @Override
        public void run() {
            for (; ;) {
                ThreadUtil.sleepMilliSeconds(SLEEP_GAP);
                System.out.println(getName() + " ,守护状态为：" + isDaemon());
            }
        }
    }

    public static void main(String[] args) {
        Thread daemonThread = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                Thread normalThread = new NormalThread();
                normalThread.start();
            }
        }, "daemonThread");
        daemonThread.setDaemon(true);
        daemonThread.start();

        ThreadUtil.sleepMilliSeconds(SLEEP_GAP);
        System.out.println(ThreadUtil.getCurThreadName() + "运行结束.");

    }
}
