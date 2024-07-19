package com.kim.notsafe;

public class NotSafePlus {
    private Integer amount = 0;

    public synchronized void selfPlus() {
        amount++;
    }

    public Integer getAmount() {
        return amount;
    }
}
