package com.kim.thread;

import com.kim.utils.ThreadUtil;

public class CreateDemo2 {
    public static final int MAX_TURN = 5;
    static int threadNo = 1;
    static class RunTarget implements Runnable {
        @Override
        public void run() {
            for (int i = 1; i < MAX_TURN; i++) {
                System.out.println(ThreadUtil.getCurThreadName() + " 轮次：" + i);
            }
            System.out.println(ThreadUtil.getCurThreadName() + " 运行结束.");
        }
    }

    public static void main(String[] args) {
        Thread thread = null;
        for (int i = 0; i < 2; i++) {
            RunTarget target = new RunTarget();
            thread = new Thread(target, "RunnableThread" + threadNo++);
            thread.start();
        }

        for (int i = 0; i < 2; i++) {
            thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int j = 0; j < MAX_TURN; j++) {
                        System.out.println(ThreadUtil.getCurThreadName() + " 轮次：" + j);
                    }
                    System.out.println(ThreadUtil.getCurThreadName() + " 运行结束.");
                }
            }, "RunnableThread" + threadNo++);

            thread.start();
        }

        for (int i = 0; i < 2; i++) {
            thread = new Thread(() -> {
                for (int j = 0; j < MAX_TURN; j++) {
                    System.out.println(ThreadUtil.getCurThreadName() + " 轮次：" + j);
                }
                System.out.println(ThreadUtil.getCurThreadName() + " 运行结束.");
            }, "RunnableThread" + threadNo++);

            thread.start();
        }
        System.out.println(ThreadUtil.getCurThreadName() + " 运行结束.");
    }
}
