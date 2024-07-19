package com.kim.distributedlock.controller;

import com.kim.distributedlock.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StockController {
    @Autowired
    private StockService stockService;

    @GetMapping("stock/deduct")
    public String deduct() {
//        stockService.deduct();
        stockService.deductLock();
        return "hello stock deduct!";
    }

    @GetMapping("test/fair/lock/{id}")
    public String testFairLock(@PathVariable("id") Long id) {
        this.stockService.testFairLock(id);
        return "hello test fair lock";
    }


    @GetMapping("test/read/lock")
    public String readLock() {
        this.stockService.testReadLock();
        return "read lock";
    }

    @GetMapping("test/write/lock")
    public String writeLock() {
        this.stockService.testWriteLock();
        return "write lock";
    }

    @GetMapping("test/semaphore")
    public String semaphore() {
        this.stockService.semaphore();
        return "semaphore";
    }

    @GetMapping("test/latch")
    public String testLatch() {
        this.stockService.latch();
        return "latch班长锁门了";
    }


    @GetMapping("test/countdown")
    public String testCountDown() {
        this.stockService.countDown();
        return "countDown同学出来了";
    }


    @GetMapping("test/curator/read/lock")
    public String readLockCurator() {
        this.stockService.testReadLockCurator();
        return "read lock";
    }

    @GetMapping("test/curator/write/lock")
    public String writeLockCurator() {
        this.stockService.testWriteLockCurator();
        return "write lock";
    }

    @GetMapping("test/share/count")
    public String shareCount() {
        this.stockService.shareCount();
        return "测试共享计数器";
    }

}
