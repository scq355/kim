package com.kim.thread;

import com.kim.utils.ThreadUtil;

public class DaemonDemo {

    public static final int SLEEP_GAP = 500;
    public static final int MAX_TURN = 4;

    static class DaemonThread extends Thread {
        public DaemonThread() {
            super("daemonThread");
        }

        @Override
        public void run() {
            System.out.println(ThreadUtil.getCurThreadName() + " --daemonThread开始");
            for (int i = 1; ; i++) {
                System.out.println(ThreadUtil.getCurThreadName() + "--轮次：" + i);
                System.out.println(ThreadUtil.getCurThreadName() + "--守护状态为：" + isDaemon());
                ThreadUtil.sleepMilliSeconds(SLEEP_GAP);
            }
        }
    }

    public static void main(String[] args) {
        DaemonThread daemonThread = new DaemonThread();
        daemonThread.setDaemon(true);
        daemonThread.start();

        Thread userThread = new Thread(() -> {
            System.out.println(ThreadUtil.getCurThreadName() + ">>用户线程开始");
            for (int i = 1; i <= MAX_TURN ; i++) {
                System.out.println(ThreadUtil.getCurThreadName() + ">>轮次: " + i);
                System.out.println(ThreadUtil.getCurThreadName() + ">>守护状态为: " + Thread.currentThread().isDaemon());
                ThreadUtil.sleepMilliSeconds(SLEEP_GAP);
            }
            System.out.println(ThreadUtil.getCurThreadName() + ">>用户线程结束.");
        }, "userThread");

        userThread.start();
        System.out.println(ThreadUtil.getCurThreadName() + "守护状态为：" + Thread.currentThread().isDaemon());
        System.out.println(ThreadUtil.getCurThreadName() + "运行结束.");
    }
}
