package com.kim.happensbefore;

import com.kim.utils.Print;

public class JoinExample {
    private int x = 0;
    private int y = 0;
    private boolean flag = false;

    public static void main(String[] args) throws InterruptedException {
        Thread.currentThread().setName("线程A");
        JoinExample joinExample = new JoinExample();

        Thread threadB = new Thread(joinExample::writer, "线程B");
        threadB.start();
        threadB.join();
        Print.tcfo("x:" + joinExample.x);
        Print.tcfo("y:" + joinExample.y);
        Print.tcfo("flag:" + joinExample.flag);
        Print.tcfo("本线程结束");
    }

    public void writer() {
        Print.tcfo("开始赋值操作");
        this.x = 10;
        this.y = 20;
        this.flag = true;
    }
}
