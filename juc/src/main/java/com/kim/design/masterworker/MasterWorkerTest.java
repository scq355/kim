package com.kim.design.masterworker;

import com.kim.utils.Print;
import com.kim.utils.ThreadUtil;

import java.util.concurrent.TimeUnit;

public class MasterWorkerTest {

    static class SimpleTask extends Task<Integer> {
        @Override
        protected Integer doExecute() {
            Print.tcfo("task " + getId() + " is done");
            return getId();
        }
    }

    public static void main(String[] args) {
        Master<SimpleTask, Integer> master = new Master<>(4);
        ThreadUtil.scheduleAtFixedRate(() -> master.submit(new SimpleTask()), 2, TimeUnit.SECONDS);

        ThreadUtil.scheduleAtFixedRate(master::printResult, 5, TimeUnit.SECONDS);
    }
}
