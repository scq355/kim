package com.kim.locks;

import lombok.Data;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class CLHLock implements Lock {
    // 当前节点的线程本地变量
    private static ThreadLocal<Node> curNodeLocal = new ThreadLocal();
    // CHLLock队列的尾指针

    private AtomicReference<Node> tail = new AtomicReference<>(null);

    public CLHLock() {
        tail.getAndSet(Node.EMPTY);
    }

    /**
     * 加锁：将节点添加到等待队列尾部
     */
    @Override
    public void lock() {
        Node curNode = new Node(true, null);
        Node prevNode = tail.get();
        // CAS自旋：将当前节点插入队列尾部
        while (!tail.compareAndSet(prevNode, curNode)) {
            prevNode = tail.get();
        }
        // 设置前驱节点
        curNode.setPrevNode(prevNode);

        // 自旋，监听前驱节点locked变量，直到其值为false
        // 若前驱节点的locked=true，表示前一个线程还在抢占或者占有锁
        while (curNode.getPrevNode().isLocked()) {
            // 让出CPU时间片，提高新性能
            Thread.yield();
        }
        // 当前线程获得到了锁
        curNodeLocal.set(curNode);
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public void unlock() {
        Node curNode = curNodeLocal.get();
        curNode.setLocked(false);
        curNode.setPrevNode(null);
        curNodeLocal.set(null);
    }

    @Override
    public Condition newCondition() {
        return null;
    }


    @Data
    static class Node {
        // true：当前线程正在抢占锁或者已经占有了锁
        volatile boolean locked;
        // 前一个节点，需要监听其locked字段
        Node prevNode;

        public Node(boolean locked, Node prevNode) {
            this.locked = locked;
            this.prevNode = prevNode;
        }

        public static final Node EMPTY = new Node(false, null);
    }
}
