package com.kim.design.masterworker;

import com.kim.utils.Print;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicLong;

public class Master<T extends Task, R> {
    private HashMap<String, Worker<T, R>> workers = new HashMap<>();

    private LinkedBlockingQueue<T> taskQueue = new LinkedBlockingQueue<>();

    protected Map<String, R> resultMap = new ConcurrentHashMap<>();

    /**
     * 任务调度线程
     */
    private Thread thread = null;

    private AtomicLong sum = new AtomicLong(0);

    public Master(int workerCount) {
        for (int i = 0; i < workerCount; i++) {
            Worker<T, R> worker = new Worker<>();
            workers.put("子节点：" + i, worker);
        }
        thread = new Thread(this::execute);
        thread.start();
    }

    public void submit(T task) {
        taskQueue.add(task);
    }

    public void resultCallBack(Object o) {
        Task<R> task = (Task<R>) o;
        String taskName = "Worker:" + task.getWorkerId() + "- Task:" + task.getId();
        R result = task.getResult();
        resultMap.put(taskName, result);
        sum.getAndAdd((Integer) result);
    }

    public void execute() {
        for (;;) {
            workers.forEach((key, worker) -> {
                T task = null;
                try {
                    task = this.taskQueue.take();
                    worker.submit(task, this::resultCallBack);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }

    public void printResult() {
        Print.tco("--------------sum is : " + sum.get());
        for (Map.Entry<String, R> entry : resultMap.entrySet()) {
            String taskName = entry.getKey();
            Print.fo(taskName + ":" + entry.getValue());
        }
    }


}
