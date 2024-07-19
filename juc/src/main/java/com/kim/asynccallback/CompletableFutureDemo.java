package com.kim.asynccallback;

import org.junit.Test;
import com.kim.utils.Print;
import com.kim.utils.ThreadUtil;

import java.util.concurrent.*;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

public class CompletableFutureDemo {

    @Test
    public void runAsyncDemo() throws ExecutionException, InterruptedException, TimeoutException {
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            ThreadUtil.sleepSeconds(1);
            Print.tcfo("run end...");
        });

        future.get(2, TimeUnit.SECONDS);
    }

    @Test
    public void supplyAsyncDemo() throws ExecutionException, InterruptedException, TimeoutException {
        CompletableFuture<Long> future = CompletableFuture.supplyAsync(() -> {
            long start = System.currentTimeMillis();
            ThreadUtil.sleepSeconds(1);
            Print.tcfo("run end...");
            return System.currentTimeMillis() - start;
        });

        Long time = future.get(2, TimeUnit.SECONDS);
        Print.tcfo("异步执行耗时=" + time / 1000 + "sec");
    }

    @Test
    public void whenCompleteDemo() throws ExecutionException, InterruptedException {
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            ThreadUtil.sleepSeconds(1);
            Print.tcfo("抛出异常！");
            throw new RuntimeException("发生异常");
        });

        future.whenComplete(new BiConsumer<Void, Throwable>() {
            @Override
            public void accept(Void unused, Throwable throwable) {
                Print.tcfo("执行完成");
            }
        });

        future.exceptionally(new Function<Throwable, Void>() {
            @Override
            public Void apply(Throwable throwable) {
                Print.tcfo("执行失败" + throwable.getMessage());
                return null;
            }
        });

        future.get();
    }

    @Test
    public void handleDemo() throws ExecutionException, InterruptedException {
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            ThreadUtil.sleepSeconds(1);
            Print.tcfo("抛出异常！");
            throw new RuntimeException("发生异常");
        });

        future.handle(new BiFunction<Void, Throwable, Object>() {
            @Override
            public Object apply(Void unused, Throwable throwable) {
                if (throwable == null) {
                    Print.tcfo("没有发生异常");
                } else {
                    Print.tcfo("sorry, 发生了异常");
                }
                return null;
            }
        });

        future.get();
    }

    @Test
    public void threadPoolDemo() throws Exception {
        ThreadPoolExecutor pool = ThreadUtil.getMixedTargetThreadPool();
        CompletableFuture<Long> future = CompletableFuture.supplyAsync(() -> {
            long start = System.currentTimeMillis();
            Print.tcfo("run begin");
            ThreadUtil.sleepMilliSeconds(1000);
            Print.tcfo("run end");
            return System.currentTimeMillis() - start;
        }, pool);
        Long time = future.get(2, TimeUnit.SECONDS);
        Print.tcfo("异步执行耗时=" + time / 1000 + "sec");
    }

    @Test
    public void thenApplyDemo() throws Exception {
        CompletableFuture<Long> future = CompletableFuture.supplyAsync(new Supplier<Long>() {
            @Override
            public Long get() {
                long firstStep = 10L + 10L;
                Print.tcfo("firstStep outcome is " + firstStep);
                return firstStep;
            }
        }).thenApplyAsync(new Function<Long, Long>() {
            @Override
            public Long apply(Long firstStepOutCome) {
                long secondStep = firstStepOutCome * 2;
                Print.tcfo("secondStep outcome is " + secondStep);
                return secondStep;
            }
        });

        Long result = future.get();
        Print.tcfo(" outcome is " + result);
    }

    @Test
    public void thenComposeDemo() throws Exception {
        CompletableFuture<Long> future = CompletableFuture.supplyAsync(new Supplier<Long>() {
            @Override
            public Long get() {
                long firstStep = 10L + 10L;
                Print.tcfo("firstStep outcome is " + firstStep);
                return firstStep;
            }
        }).thenCompose(new Function<Long, CompletionStage<Long>>() {
            @Override
            public CompletionStage<Long> apply(Long firstStepOutCome) {
                return CompletableFuture.supplyAsync(new Supplier<Long>() {
                    @Override
                    public Long get() {
                        long secondStep = firstStepOutCome * 2;
                        Print.tcfo("secondStep outcome is " + secondStep);
                        return secondStep;
                    }
                });
            }
        });

        Long result = future.get();
        Print.tcfo(" outcome is " + result);
    }

    @Test
    public void thenCombineDemo() throws Exception {
        CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(new Supplier<Integer>() {
            @Override
            public Integer get() {
                Integer firstStep = 10 + 10;
                Print.tcfo("firstStep outcome is " + firstStep);
                return firstStep;
            }
        });

        CompletableFuture<Integer> future2 = CompletableFuture.supplyAsync(new Supplier<Integer>() {
            @Override
            public Integer get() {
                Integer secondStep = 10 + 10;
                Print.tcfo("secondStep outcome is " + secondStep);
                return secondStep;
            }
        });

        CompletableFuture<Integer> future3 = future1.thenCombine(future2, new BiFunction<Integer, Integer, Integer>() {
            @Override
            public Integer apply(Integer step1OutCome, Integer step2OutCome) {
                return step1OutCome * step2OutCome;
            }
        });

        Integer result = future3.get();
        Print.tcfo(" outcome is " + result);
    }

    @Test
    public void allOfDemo() {
        CompletableFuture<Void> future1 = CompletableFuture.runAsync(() -> Print.tcfo("模拟异步任务1"));
        CompletableFuture<Void> future2 = CompletableFuture.runAsync(() -> Print.tcfo("模拟异步任务2"));
        CompletableFuture<Void> future3 = CompletableFuture.runAsync(() -> Print.tcfo("模拟异步任务3"));
        CompletableFuture<Void> future4 = CompletableFuture.runAsync(() -> Print.tcfo("模拟异步任务4"));
        CompletableFuture<Void> future5 = CompletableFuture.runAsync(() -> Print.tcfo("模拟异步任务5"));
        CompletableFuture<Void> all = CompletableFuture.allOf(future5, future1, future4, future2, future3);
        all.join();
    }

    @Test
    public void applyToEitherDemo() throws Exception {
        CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(new Supplier<Integer>() {
            @Override
            public Integer get() {
                Integer firstStep = 10 + 10;
                Print.tcfo("firstStep outcome is " + firstStep);
                return firstStep;
            }
        });

        CompletableFuture<Integer> future2 = CompletableFuture.supplyAsync(new Supplier<Integer>() {
            @Override
            public Integer get() {
                Integer firstStep = 100 + 100;
                Print.tcfo("secondStep outcome is " + firstStep);
                return firstStep;
            }
        });

        CompletableFuture<Integer> future3 = future1.applyToEither(future2, new Function<Integer, Integer>() {
            @Override
            public Integer apply(Integer eitherOutCome) {
                return eitherOutCome;
            }
        });

        Integer result = future3.get();
        Print.tcfo(" outcome is " + result);
    }
}
