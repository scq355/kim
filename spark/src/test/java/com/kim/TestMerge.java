package com.kim;

import java.util.HashMap;
import java.util.Map;

public class TestMerge {

    public static void main(String[] args) {
        Map<String, Long> b1 = new HashMap<>();
        Map<String, Long> b1 = new HashMap<>();
        Map<String, Long> b1Map = b1.getCityMap();
        Map<String, Long> b2Map = b1.getCityMap();

        for (Map.Entry<String, Long> b2Entry : b2Map.entrySet()) {
            String k = b2Entry.getKey();
            Long v = b2Entry.getValue();
            if (b1Map.get(k) == null) {
                b1Map.put(k, v);
            } else {
                b1Map.put(k, b1Map.get(k) + v);
            }
        }
        return b1;
    }
}
