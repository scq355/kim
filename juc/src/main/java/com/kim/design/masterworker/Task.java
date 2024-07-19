package com.kim.design.masterworker;


import lombok.Data;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

@Data
public class Task<R> {
    static AtomicInteger index = new AtomicInteger(1);

    public Consumer<Task<R>> resultAction;

    private int id;

    private int workerId;

    R result = null;

    public Task() {
        this.id = index.getAndIncrement();
    }

    public void execute() {
        this.result = this.doExecute();
        resultAction.accept(this);
    }

    /**
     * 子类实现
     */
    protected R doExecute() {
        return null;
    }
}
