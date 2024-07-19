package com.kim.distributedlock.service;

import com.kim.distributedlock.mapper.LockMapper;
import com.kim.distributedlock.mapper.StockMapper;
import com.kim.distributedlock.utils.DistributedLockClient;
import com.kim.distributedlock.utils.DistributedRedisLock;
import com.kim.distributedlock.zk.DistributedZkLock;
import com.kim.distributedlock.zk.ZkClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.*;
import org.apache.curator.framework.recipes.shared.SharedCount;
import org.redisson.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
@Service
//@Scope(scopeName = "prototype", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class StockService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    //    private Stock stock = new Stock();
//
    private ReentrantLock lock = new ReentrantLock();

//    public synchronized void deduct() {
//        stock.setStock(stock.getStock() - 1);
//        log.info("库存余量={}", stock.getStock());
//    }


//    public void deductLock() {
//        lock.lock();
//        try {
//            stock.setStock(stock.getStock() - 1);
//            log.info("库存余量={}", stock.getStock());
//        } finally {
//            lock.unlock();
//        }
//    }

    @Autowired
    private StockMapper stockMapper;

    @Autowired
    private DistributedLockClient distributedLockClient;

    @Autowired
    private ZkClient zkClient;


//    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
//    public void deductLock() {
//        lock.lock();
//        try {
//            Stock stock = stockMapper.selectOne(new QueryWrapper<Stock>().eq("product_code", "1001"));
//            if (stock != null && stock.getCount() > 0) {
//                stock.setCount(stock.getCount() - 1);
//                stockMapper.updateById(stock);
//            }
//        } finally {
//            lock.unlock();
//        }
//    }


//    public void deductLock() {
//        lock.lock();
//        try {
//            Stock stock = stockMapper.selectOne(new QueryWrapper<Stock>().eq("product_code", "1001"));
//            if (stock != null && stock.getCount() > 0) {
//                stock.setCount(stock.getCount() - 1);
//                log.info("剩余={}", stock.getCount());
//                stockMapper.updateById(stock);
//            }
//        } finally {
//            lock.unlock();
//        }
//    }


//    @Transactional
//    public void deductLock() {
////        lock.lock();
//        try {
//            stockMapper.updateStock("1001", 1);
//        } finally {
////            lock.unlock();
//        }
//    }


//    @Transactional
//    public void deductLock() {
//        // 查询并锁定库存
//        List<Stock> stockList = stockMapper.queryStock("1001");
//        // 判断库存是否充足
//        Stock stock = stockList.get(0);
//        if (stock != null && stock.getCount() > 0) {
//            // 扣减库存
//            stock.setCount(stock.getCount() - 1);
//            stockMapper.updateById(stock);
//        }
//    }


//    @Transactional
//    public void deductLock() {
//        // 查询并锁定库存
//        List<Stock> stockList = stockMapper.selectList(new QueryWrapper<Stock>().eq("product_code", "1001"));
//        // 判断库存是否充足
//        Stock stock = stockList.get(0);
//        if (stock != null && stock.getCount() > 0) {
//            // 扣减库存
//            stock.setCount(stock.getCount() - 1);
//            Integer version = stock.getVersion();
//            stock.setVersion(version + 1);
//            // update tb_stock set count = 4995, version = version + 1 where id = 1 and version = 0;
//            if (stockMapper.update(stock, new UpdateWrapper<Stock>().eq("id", stock.getId()).eq("version", version)) == 0) {
//                // 如果更新失败，进行重试
//                try {
//                    Thread.sleep(100);
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
//                deductLock();
//            }
//        }
//    }

//    public void deductLock() {
//        this.redisTemplate.execute(new SessionCallback<Object>() {
//            @Override
//            public Object execute(RedisOperations operations) throws DataAccessException {
//                operations.watch("stock");
//                // 查询库存信息
//                String stock = operations.opsForValue().get("stock").toString();
//                // 判断库存是否充足
//                if (stock != null && stock.length() != 0) {
//                    Integer st = Integer.valueOf(stock);
//                    if (st > 0) {
//                        operations.multi();
//                        // 扣减库存
//                        operations.opsForValue().set("stock", String.valueOf(--st));
//                        // 执行事务
//                        List exec = operations.exec();
//                        // 执行失败，重试
//                        if (CollectionUtils.isEmpty(exec)) {
//                            try {
//                                Thread.sleep(100);
//                            } catch (InterruptedException e) {
//                                throw new RuntimeException(e);
//                            }
//                            deductLock();
//                        }
//                        return exec;
//                    }
//                }
//                return null;
//            }
//        });
//
//    }


//    public void deductLock() {
//        String uuid = UUID.randomUUID().toString();
//        // 加锁 setnx
//        while (!this.redisTemplate.opsForValue().setIfAbsent("lock", uuid, 3, TimeUnit.SECONDS)) {
//            try {
//                Thread.sleep(50);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//        }
//        try {
//            // 无原子性
//            // this.redisTemplate.expire("lock", 3, TimeUnit.SECONDS);
//            // 查询库存信息
//            String stock = redisTemplate.opsForValue().get("stock");
//            // 判断库存是否充足
//            if (stock != null && stock.length() != 0) {
//                Integer st = Integer.valueOf(stock);
//                if (st > 0) {
//                    // 扣减库存
//                    redisTemplate.opsForValue().set("stock", String.valueOf(--st));
//                }
//            }
//        } finally {
//            // 解锁
//            // 保证原子性
//            String unlockScript = "" +
//                    "if redis.call('get', KEYS[1]) == ARGV[1] then " +
//                    "    return redis.call('del', KEYS[1]) " +
//                    "else " +
//                    "    return 0 " +
//                    "end";
//            this.redisTemplate.execute(new DefaultRedisScript<>(unlockScript, Boolean.class), Collections.singletonList("lock"), uuid);
//            // 存在原子性问题
////            if (uuid.equals(this.redisTemplate.opsForValue().get("lock"))) { // 防误删
////                this.redisTemplate.delete("lock");
////            }
//        }
//    }


//    public void deductLock() {
//        DistributedRedisLock redisLock = this.distributedLockClient.getRedisLock("lock");
//        redisLock.lock();
//        try {
//            // 查询库存信息
//            String stock = redisTemplate.opsForValue().get("stock");
//            // 判断库存是否充足
//            if (stock != null && stock.length() != 0) {
//                Integer st = Integer.valueOf(stock);
//                if (st > 0) {
//                    // 扣减库存
//                    redisTemplate.opsForValue().set("stock", String.valueOf(--st));
//                }
//            }
////            TimeUnit.SECONDS.sleep(3000);
////            this.test();
////        } catch (InterruptedException e) {
////            throw new RuntimeException(e);
//        } finally {
//            redisLock.unlock();
//        }
//    }

    public void test() {
        DistributedRedisLock redisLock = this.distributedLockClient.getRedisLock("lock");
        redisLock.lock();
        log.info("测试可重入锁");
        redisLock.unlock();
    }


    public void testZk() {
        DistributedZkLock zkLock = this.zkClient.getLock("lock");
        zkLock.lock();
        log.info("测试可重入锁");
        zkLock.unlock();
    }

    public void testCurator(InterProcessMutex mutex) throws Exception {
        mutex.acquire();
        log.info("测试可重入锁");
        mutex.release();
    }


    public static void main(String[] args) {
//        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(3);
//        log.info("定时任务初始时间={}", System.currentTimeMillis());
//        scheduledExecutorService.scheduleAtFixedRate(() -> log.info("定时任务执行时间={}", System.currentTimeMillis()),
//                5, 10, TimeUnit.SECONDS);

//        log.info("定时任务初始时间={}", System.currentTimeMillis());
//        new Timer().schedule(new TimerTask() {
//            @Override
//            public void run() {
//                log.info("定时任务初始时间={}", System.currentTimeMillis());
//            }
//        }, 5000, 10000);

//        Semaphore semaphore = new Semaphore(3);
//        for (int i = 0; i < 6; i++) {
//            new Thread(() -> {
//                try {
//                    semaphore.acquire();
//                    log.info(Thread.currentThread().getName() + "抢到了停车位");
//                    TimeUnit.SECONDS.sleep(10);
//                    log.info(Thread.currentThread().getName() + "停了一会儿开走了");
//                    semaphore.release();
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
//            }, i + "号车").start();
//        }


        CountDownLatch countDownLatch = new CountDownLatch(6);
        for (int i = 0; i < 6; i++) {
            new Thread(() -> {
                try {
                    log.info("{}准备出门了...", Thread.currentThread().getName());
                    TimeUnit.SECONDS.sleep(new Random().nextInt(5));
                    log.info("{}出门了...", Thread.currentThread().getName());
                    countDownLatch.countDown();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }, i + "号同学").start();
        }

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        log.info("班长锁门");
    }


    @Autowired
    private RedissonClient redissonClient;

//    public void deductLock() {
//        RLock rLock = this.redissonClient.getLock("lock");
//        rLock.lock();
//        try {
//            // 查询库存信息
//            String stock = redisTemplate.opsForValue().get("stock");
//            // 判断库存是否充足
//            if (stock != null && stock.length() != 0) {
//                Integer st = Integer.valueOf(stock);
//                if (st > 0) {
//                    // 扣减库存
//                    redisTemplate.opsForValue().set("stock", String.valueOf(--st));
//                }
//            }
//        } finally {
//            rLock.unlock();
//        }
//    }

//    public void deductLock() {
//        DistributedZkLock lock = this.zkClient.getLock("lock");
//        lock.lock();
//        try {
//            // 查询库存信息
//            String stock = redisTemplate.opsForValue().get("stock");
//            // 判断库存是否充足
//            if (stock != null && stock.length() != 0) {
//                Integer st = Integer.valueOf(stock);
//                if (st > 0) {
//                    // 扣减库存
//                    redisTemplate.opsForValue().set("stock", String.valueOf(--st));
//                }
//            }
//            this.testZk();
//        } finally {
//            lock.unlock();
//        }
//    }

    @Autowired
    private CuratorFramework curatorFramework;

//    public void deductLock() {
//        InterProcessMutex mutex = new InterProcessMutex(curatorFramework, "/curator/locks");
//        try {
//            mutex.acquire();
//            // 查询库存信息
//            String stock = redisTemplate.opsForValue().get("stock");
//            // 判断库存是否充足
//            if (stock != null && stock.length() != 0) {
//                Integer st = Integer.valueOf(stock);
//                if (st > 0) {
//                    // 扣减库存
//                    redisTemplate.opsForValue().set("stock", String.valueOf(--st));
//                }
//            }
//            this.testCurator(mutex);
//        } catch (Exception e) {
//            log.error("执行异常", e);
//        } finally {
//            try {
//                mutex.release();
//            } catch (Exception e) {
//                log.error("解锁异常", e);
//            }
//        }
//    }


    @Autowired
    private LockMapper lockMapper;

//    public void deductLock() {
//        try {
//            Lock tbLock = Lock.builder().lockName("lock").build();
//            // 加锁
//            this.lockMapper.insert(tbLock);
//            // 查询库存信息
//            String stock = redisTemplate.opsForValue().get("stock");
//            // 判断库存是否充足
//            if (stock != null && stock.length() != 0) {
//                Integer st = Integer.valueOf(stock);
//                if (st > 0) {
//                    // 扣减库存
//                    redisTemplate.opsForValue().set("stock", String.valueOf(--st));
//                }
//            }
//            // 解锁
//            this.lockMapper.deleteById(tbLock.getId());
//        } catch (Exception e) {
//            log.error("异常", e);
//            // 重试
//            try {
//                Thread.sleep(50);
//            } catch (InterruptedException ex) {
//                throw new RuntimeException(ex);
//            }
//            this.deductLock();
//        }
//    }



    public void deductLock() {
        // 不可重入锁
        InterProcessSemaphoreMutex mutex = new InterProcessSemaphoreMutex(curatorFramework, "/curator/lock");
        try {
            mutex.acquire();
            // 查询库存信息
            String stock = redisTemplate.opsForValue().get("stock");
            // 判断库存是否充足
            if (stock != null && stock.length() != 0) {
                Integer st = Integer.valueOf(stock);
                if (st > 0) {
                    // 扣减库存
                    redisTemplate.opsForValue().set("stock", String.valueOf(--st));
                }
            }
        } catch (Exception e) {
            log.error("异常", e);
            // 重试
        } finally {
            try {
                mutex.release();
            } catch (Exception ex) {
                log.error("解锁异常", ex);
            }
        }
    }


    public void testFairLock(Long id) {
        RLock fairLock = this.redissonClient.getFairLock("fairLock");
        fairLock.lock();
        try {
            TimeUnit.SECONDS.sleep(20);
            log.info("测试公平锁>>>>>>>>>>>>>>>>>>>>>>{}", id);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            fairLock.unlock();
        }
    }

    public void testReadLock() {
        RReadWriteLock rwLock = this.redissonClient.getReadWriteLock("rwLock");
        rwLock.readLock().lock(10, TimeUnit.SECONDS);
        log.info("读操作");
    }

    public void testWriteLock() {
        RReadWriteLock rwLock = this.redissonClient.getReadWriteLock("rwLock");
        rwLock.writeLock().lock(10, TimeUnit.SECONDS);
        log.info("写操作");
    }

//    Semaphore semaphore = new Semaphore(3);

    public void semaphore() {
        RSemaphore semaphore = this.redissonClient.getSemaphore("semaphore");
        semaphore.trySetPermits(3);
        try {
            semaphore.acquire();
            log.info("{}获取资源，开始处理业务逻辑", Thread.currentThread().getName());
            this.redisTemplate.opsForList().rightPush("log", Thread.currentThread().getName() + "获取资源，开始处理业务逻辑");
            TimeUnit.SECONDS.sleep(10 + new Random().nextInt(10));
            log.info("{}释放资源", Thread.currentThread().getName());
            this.redisTemplate.opsForList().rightPush("log", Thread.currentThread().getName() + "释放资源");
            semaphore.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public void curatorSemaphore() {
        InterProcessSemaphoreV2 semaphore = new InterProcessSemaphoreV2(curatorFramework, "/curator/locks", 5);
        try {
            Lease lease = semaphore.acquire();
            log.info("{}获取资源，开始处理业务逻辑", Thread.currentThread().getName());
            this.redisTemplate.opsForList().rightPush("log", Thread.currentThread().getName() + "获取资源，开始处理业务逻辑");
            TimeUnit.SECONDS.sleep(10 + new Random().nextInt(10));
            log.info("{}释放资源", Thread.currentThread().getName());
            this.redisTemplate.opsForList().rightPush("log", Thread.currentThread().getName() + "释放资源");
            semaphore.returnLease(lease);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //    CountDownLatch countDownLatch = new CountDownLatch(6);
    public void latch() {
        RCountDownLatch countDownLatch = this.redissonClient.getCountDownLatch("cdl");
        countDownLatch.trySetCount(6);
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


    }

    public void countDown() {
        RCountDownLatch countDownLatch = this.redissonClient.getCountDownLatch("cdl");
        countDownLatch.countDown();
    }

    public void testReadLockCurator() {
        InterProcessReadWriteLock readWriteLock = new InterProcessReadWriteLock(curatorFramework, "/curator/rwLock");
        try {
            readWriteLock.readLock().acquire(10, TimeUnit.SECONDS);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void testWriteLockCurator() {
        InterProcessReadWriteLock readWriteLock = new InterProcessReadWriteLock(curatorFramework, "/curator/rwLock");
        try {
            readWriteLock.writeLock().acquire(10, TimeUnit.SECONDS);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void shareCount() {
        SharedCount sharedCount = new SharedCount(curatorFramework, "/curator/sharedcount", 100);
        try {
            sharedCount.start();
            int count = sharedCount.getCount();
            int random = new Random().nextInt(1000);
            sharedCount.setCount(random);
            log.info("共享计数器初始值={}, 修改后的值={}", count, random);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
