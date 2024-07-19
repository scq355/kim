package com.kim.happensbefore;

import com.kim.utils.Print;

public class StartExample {
    private int x = 0;
    private int y = 0;
    private boolean flag = false;

    public static void main(String[] args) {
        Thread.currentThread().setName("线程A");
        StartExample startExample = new StartExample();
        Thread threadB = new Thread(startExample::writer, "线程B");
        Print.tcfo("开始赋值操作");
        startExample.x = 10;
        startExample.y = 20;
        startExample.flag = true;

        threadB.start();
        Print.tcfo("线程结束");
    }

    public void writer() {
        Print.tcfo("x:" + x);
        Print.tcfo("y:" + y);
        Print.tcfo("flag:" + flag);
    }
}
