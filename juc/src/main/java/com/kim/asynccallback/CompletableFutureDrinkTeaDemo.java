package com.kim.asynccallback;

import com.kim.utils.Print;
import com.kim.utils.ThreadUtil;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiFunction;

public class CompletableFutureDrinkTeaDemo {

    public static final int SLEEP_GAP = 3;

    public static void main(String[] args) {
        CompletableFuture<Boolean> hotJob = CompletableFuture.supplyAsync(() -> {
            Print.tcfo("洗好水壶");
            Print.tcfo("灌上凉水");
            Print.tcfo("放在火上");
            ThreadUtil.sleepSeconds(SLEEP_GAP);
            Print.tcfo("水开了");
            return true;
        });

        CompletableFuture<Boolean> washJob = CompletableFuture.supplyAsync(() -> {
            Print.tcfo("洗茶壶");
            Print.tcfo("洗茶杯");
            Print.tcfo("拿茶叶");
            ThreadUtil.sleepSeconds(SLEEP_GAP);
            Print.tcfo("洗完了");
            return true;
        });

        CompletableFuture<String> drinkJob = hotJob.thenCombine(washJob, new BiFunction<Boolean, Boolean, String>() {
            @Override
            public String apply(Boolean hotOk, Boolean washOk) {
                if (hotOk && washOk) {
                    Print.tcfo("泡茶喝，茶喝完");
                    return "茶喝完了";
                }
                return "没有喝到茶";
            }
        });

        Print.tcfo(drinkJob.join());
    }
}
