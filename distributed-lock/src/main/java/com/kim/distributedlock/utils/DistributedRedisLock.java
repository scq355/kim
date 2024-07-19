package com.kim.distributedlock.utils;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;

import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

@Data
@Slf4j
@RequiredArgsConstructor
public class DistributedRedisLock implements Lock {

    private StringRedisTemplate redisTemplate;

    private String lockName;
    private String uuid;
    private long expire = 30;

    public DistributedRedisLock(StringRedisTemplate redisTemplate, String lockName, String uuid) {
        this.redisTemplate = redisTemplate;
        this.lockName = lockName;
        this.uuid = uuid + ":" + Thread.currentThread().getId();
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
            return this.tryLock(-1L, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        if (time != -1) {
            this.expire = unit.toSeconds(time);
        }
        String lockScript = "" +
                "if redis.call('exists', KEYS[1]) == 0 or redis.call('hexists', KEYS[1], ARGV[1]) == 1 then " +
                "    redis.call('hincrby', KEYS[1], ARGV[1], 1) " +
                "    redis.call('expire', KEYS[1], ARGV[2]) " +
                "    return 1 " +
                "else " +
                "    return 0 " +
                "end";
        while (Boolean.FALSE.equals(this.redisTemplate.execute(new DefaultRedisScript<>(lockScript, Boolean.class), Collections.singletonList(lockName), uuid, String.valueOf(expire)))) {
            Thread.sleep(50);
        }
        // 加锁成功，返回之前自动开启定时器，自动续期
        this.renewExpire();
        return true;
    }

    @Override
    public void unlock() {
        String unlockScript = "" +
                "if redis.call('hexists', KEYS[1], ARGV[1]) == 0 then " +
                "    return nil " +
                "elseif redis.call('hincrby', KEYS[1], ARGV[1], -1) == 0 then " +
                "    return redis.call('del', KEYS[1]) " +
                "else " +
                "    return 0 " +
                "end";
        Long result = this.redisTemplate.execute(new DefaultRedisScript<>(unlockScript, Long.class), Collections.singletonList(lockName), uuid);
        if (result == null) {
            throw new IllegalMonitorStateException("this lock dos not belong to current thread");
        }
    }

    @Override
    public Condition newCondition() {
        return null;
    }

    private void renewExpire() {
        String renewScript = "" +
                "if redis.call('hexists', KEYS[1], ARGV[1]) == 1 then " +
                "    return redis.call('expire', KEYS[1], ARGV[2]) " +
                "else " +
                "    return 0 " +
                "end";
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                Boolean result = redisTemplate.execute(new DefaultRedisScript<>(renewScript, Boolean.class), Collections.singletonList(lockName), uuid, String.valueOf(expire));
                if (result) {
                    renewExpire();
                }
            }
        }, this.expire * 1000 / 3);
    }
}
