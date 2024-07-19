package com.kim.thread;

import com.kim.utils.ThreadUtil;

public class ThreadNameDemo {
    public static final int MAX_TURN = 3;

    static class RunTarget implements Runnable {
        @Override
        public void run() {
            for (int turn = 0; turn < MAX_TURN; turn++) {
                ThreadUtil.sleepMilliSeconds(500);
                System.out.println(ThreadUtil.getCurThreadName() + "线程执行轮次：" + turn);
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        RunTarget target = new RunTarget();
        new Thread(target).start();
        new Thread(target).start();
        new Thread(target).start();
        new Thread(target, "手动命名线程A").start();
        new Thread(target, "手动命名线程B").start();
        Thread.sleep(Integer.MAX_VALUE);
    }
}
