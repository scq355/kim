package com.kim.distributedlock.zk;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.zookeeper.*;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.stream.Collectors;

@Slf4j
public class DistributedZkLock implements Lock {

    private ZooKeeper zooKeeper;

    private String lockName;

    private String currentNodePath;

    public static final String ROOT_PATH = "/locks";

    private static final ThreadLocal<Integer> THREAD_LOCAL = new ThreadLocal<>();


    public DistributedZkLock(ZooKeeper zooKeeper, String lockName) {
        this.zooKeeper = zooKeeper;
        this.lockName = lockName;
        try {
            if (zooKeeper.exists(ROOT_PATH, false) == null) {
                zooKeeper.create(ROOT_PATH, null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }
        } catch (InterruptedException | KeeperException e) {
            log.error("create root node error", e);
        }
    }


    @Override
    public void lock() {
        this.tryLock();
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {
        try {
            // 判断ThreadLocal中是否已经有锁，有锁直接重入
            Integer count = THREAD_LOCAL.get();
            if (count != null && count > 0) {
                THREAD_LOCAL.set(count + 1);
                return true;
            }
            // 创建znode节点过程,为防止客户端获取到锁之后，服务器宕机之后带来死锁问题，这里创建的是临时节点
            // 所有请求获取锁时，给每一个请求创建一个临时序列化节点
            currentNodePath = this.zooKeeper.create(ROOT_PATH + "/" + lockName + "-", null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
            // 获取前置节点，若前置节点为空，则获取锁成功，否则监听前置节点
            String preNode = this.getPreNode();
            if (preNode != null) {
                // 应用闭锁思想实现阻塞功能
                CountDownLatch countDownLatch = new CountDownLatch(1);
                // 再次判断zk中前置节点是否存在，因为获取前置节点操作不具备原子性
                if (this.zooKeeper.exists(ROOT_PATH + "/" + preNode, new Watcher() {
                    @Override
                    public void process(WatchedEvent watchedEvent) {
                        countDownLatch.countDown();
                    }
                }) == null) {
                    THREAD_LOCAL.set(1);
                    return true;
                }
                countDownLatch.await();
            }
            THREAD_LOCAL.set(1);
            return true;
        } catch (InterruptedException | KeeperException e) {
            log.error("加锁异常", e);
//            try {
//                Thread.sleep(80);
//                this.tryLock();
//            } catch (InterruptedException ex) {
//                throw new RuntimeException(ex);
//            }
        }
        return false;
    }

    private String getPreNode() {
        try {
            // 获取根节点下的所有节点
            List<String> children = this.zooKeeper.getChildren(ROOT_PATH, false);
            if (CollectionUtils.isEmpty(children)) {
                throw new IllegalMonitorStateException("非法操作");
            }
            // 获取和当前节点在同一资源的锁
            List<String> nodes = children.stream().filter(node -> StringUtils.startsWith(node, lockName + "-")).collect(Collectors.toList());
            if (CollectionUtils.isEmpty(nodes)) {
                throw new IllegalMonitorStateException("非法操作");
            }
            // 节点排序
            Collections.sort(nodes);
            // 获取当前节点的下标
            String currentNode = StringUtils.substringAfterLast(currentNodePath, "/");
            int index = Collections.binarySearch(nodes, currentNode);
            if (index < 0) {
                throw new IllegalMonitorStateException("非法操作");
            } else if (index > 0) {
                // 返回前置节点
                return nodes.get(index - 1);
            }
            // 当前节点就是第一个节点，前置节点为null
            return null;
        } catch (KeeperException | InterruptedException e) {
            throw new IllegalMonitorStateException("非法操作");
        }
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public void unlock() {
        try {
            THREAD_LOCAL.set(THREAD_LOCAL.get() - 1);
            if (THREAD_LOCAL.get() == 0) {
                // 删除znode节点过程
                this.zooKeeper.delete(currentNodePath, -1);
            }
        } catch (InterruptedException | KeeperException e) {
            log.error("解锁异常", e);
        }
    }

    @Override
    public Condition newCondition() {
        return null;
    }
}
