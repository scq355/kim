package com.kim.design.forkjoin;

import org.junit.Assert;
import org.junit.Test;
import com.kim.utils.Print;

import java.util.concurrent.*;

public class ForkJoinTest {

    @Test
    public void testAccumulateTask() throws ExecutionException, InterruptedException, TimeoutException {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        AccumulateTask task = new AccumulateTask(1, 100);

        Future<Integer> future = forkJoinPool.submit(task);
        Integer sum = future.get(1, TimeUnit.SECONDS);
        Print.tcfo("最终计算结果：" +  sum);
        Assert.assertEquals(5050, (int) sum);
    }
}
