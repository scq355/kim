package com.kim.utils;

import java.util.concurrent.ThreadLocalRandom;

public class RandomUtil {

    /**
     * [1, mod]
     */
    public static int randInMod(int mod) {
        return Math.abs(ThreadLocalRandom.current().nextInt(mod) + 1);
    }

    /**
     * [0, mod)
     */
    public static int randInModLower(int mod) {
        return Math.abs(ThreadLocalRandom.current().nextInt(mod));
    }

    public static int randInRange(int low, int high) {
        return randInMod(high - low) + low;
    }
}
