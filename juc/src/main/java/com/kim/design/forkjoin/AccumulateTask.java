package com.kim.design.forkjoin;

import com.kim.utils.Print;

import java.util.concurrent.RecursiveTask;

public class AccumulateTask extends RecursiveTask<Integer> {

    private static final int THRESHOLD = 2;

    private int start;

    private int end;

    public AccumulateTask(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Integer compute() {
        int sum = 0;

        boolean canCompute = (end - start) <= THRESHOLD;

        if (canCompute) {
            for (int i = start; i <= end; i++) {
                sum += i;
            }
            Print.tcfo("执行任务，计算 " + start + " 到 " + end + " 的和，结果是：" + sum);
        } else {
            Print.tcfo("切割任务：将 " + start + " 到 " + end + " 的和一分为二");
            int middle = (start + end) / 2;

            AccumulateTask lTask = new AccumulateTask(start, middle);
            AccumulateTask rTask = new AccumulateTask(middle + 1, end);

            lTask.fork();
            rTask.fork();

            Integer lResult = lTask.join();
            Integer rResult = rTask.join();

            sum = lResult + rResult;
        }
        return sum;
    }
}
