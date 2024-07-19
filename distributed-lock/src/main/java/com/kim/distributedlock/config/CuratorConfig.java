package com.kim.distributedlock.config;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CuratorConfig {
    @Bean
    public CuratorFramework curatorFramework() {
        // 初始化重试策略，指数补偿策略，初始间隔时间，重试次数
        RetryPolicy retry = new ExponentialBackoffRetry(10000, 3);
        // 初始化客户端
        CuratorFramework client = CuratorFrameworkFactory.newClient("localhost:2181", retry);
        // 手动启动，否则部分功能无法使用
        client.start();
        return client;
    }
 }
