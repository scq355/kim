package com.kim.threadlocal;

import org.junit.Test;
import com.kim.utils.SpeedLog;
import com.kim.utils.ThreadUtil;

public class ThreadLocalTest2 {

    public void serviceMethod() {
        ThreadUtil.sleepMilliSeconds(500);
        SpeedLog.logPoint("point-1 service");
        daoMethod();
        rpcMethod();
    }

    public void daoMethod() {
        ThreadUtil.sleepMilliSeconds(400);
        SpeedLog.logPoint("point-2 dao");
    }

    public void rpcMethod() {
        ThreadUtil.sleepMilliSeconds(600);
        SpeedLog.logPoint("point-3 rpc");
    }

    @Test
    public void testSpeedLog() {
        Runnable runnable = () -> {
            SpeedLog.beginSpeedLog();
            serviceMethod();
            SpeedLog.printCost();
            SpeedLog.endSpeedLog();
        };

        new Thread(runnable).start();
        ThreadUtil.sleepSeconds(10);
    }
}
