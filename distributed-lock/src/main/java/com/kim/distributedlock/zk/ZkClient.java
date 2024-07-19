package com.kim.distributedlock.zk;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;

@Slf4j
@Component
public class ZkClient {

    private ZooKeeper zooKeeper;

    @PostConstruct
    public void init() {
        // 获取链接，项目启动时
        CountDownLatch countDownLatch = new CountDownLatch(1);
        try {
            zooKeeper = new ZooKeeper("localhost:2181", 30000, new Watcher() {
                @Override
                public void process(WatchedEvent watchedEvent) {
                    Event.KeeperState state = watchedEvent.getState();
                    if (Event.KeeperState.SyncConnected.equals(state)) {
                        log.info("获取到链接={}", watchedEvent);
                        countDownLatch.countDown();
                    } else if (Event.KeeperState.Closed.equals(state)) {
                        log.info("关闭链接");
                    }
                }
            });
            countDownLatch.await();
        } catch (IOException e) {
            log.error("发生异常", e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    @PreDestroy
    public void destory() {
        // 释放zk链接
        if (zooKeeper != null) {
            try {
                zooKeeper.close();
            } catch (InterruptedException e) {
                log.error("关闭链接异常", e);
            }
        }
    }

    public DistributedZkLock getLock(String lockName) {
        return new DistributedZkLock(zooKeeper, lockName);
    }
}
