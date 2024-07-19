package com.kim.asynccallback;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import org.junit.Test;
import com.kim.utils.Print;
import com.kim.utils.ThreadUtil;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiFunction;

public class IntegrityDemo {
    public String rpc1() {
        ThreadUtil.sleepMilliSeconds(600);
        Print.tcfo("模拟RPC调用：server1");
        return "sth. from server 1";
    }

    public String rpc2() {
        ThreadUtil.sleepMilliSeconds(600);
        Print.tcfo("模拟RPC调用：server2");
        return "sth. from server 2";
    }


    @Test
    public void rpcDemo() throws Exception {
        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(this::rpc1);
        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(this::rpc2);

        CompletableFuture<String> future3 = future1.thenCombine(future2, new BiFunction<String, String, String>() {
            @Override
            public String apply(String out1, String out2) {
                return out1 + " & " + out2;
            }
        });

        String result = future3.get();
        Print.tcfo("客户端合并最终的结果：" + result);
    }

    @Test
    public void rxJavaDemo() {
        Observable<String> observable1 = Observable.fromCallable(this::rpc1).subscribeOn(Schedulers.newThread());

        Observable<String> observable2 = Observable.fromCallable(this::rpc2).subscribeOn(Schedulers.newThread());

        Observable.merge(observable1, observable2)
                .observeOn(Schedulers.newThread())
                .toList()
                .subscribe((result) -> Print.tcfo("客户端最终合并的结果=" + result));

        ThreadUtil.sleepSeconds(Integer.MAX_VALUE);
    }
}
