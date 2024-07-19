package com.kim.utils;

import java.util.HashMap;
import java.util.Map;

public class SpeedLog {
    private static final ThreadLocal<Map<String, Long>> TIME_RECORD_LOCAL =
            ThreadLocal.withInitial(SpeedLog::initialStartTime);


    public static Map<String, Long> initialStartTime() {
        HashMap<String, Long> map = new HashMap<>();
        map.put("start", System.currentTimeMillis());
        map.put("last", System.currentTimeMillis());
        return map;
    }


    public static void beginSpeedLog() {
        Print.fo("开始耗时记录");
        TIME_RECORD_LOCAL.get();
    }

    public static void endSpeedLog() {
        TIME_RECORD_LOCAL.remove();
        Print.fo("结束耗时记录");
    }

    public static void logPoint(String point) {
        Long last = TIME_RECORD_LOCAL.get().get("last");
        // 计算耗时并保存
        long cost = System.currentTimeMillis() - last;
        TIME_RECORD_LOCAL.get().put(point + " cost:", cost);
        // 保存最近时间，供下次使用
        TIME_RECORD_LOCAL.get().put("last", System.currentTimeMillis());
    }

    public static void printCost() {
        for (Map.Entry<String, Long> entry : TIME_RECORD_LOCAL.get().entrySet()) {
            Print.fo(entry.getKey() + "=>" + entry.getValue());
        }
    }
}
