package com.mmall.concurrency.example.cache.redisson;

import com.sun.org.apache.regexp.internal.RE;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.print.attribute.standard.MediaSize;
import java.util.concurrent.TimeUnit;

/**
 * @author gongchunru
 * @email gongchunru@gmail.com
 * Date：2018/4/2 14:00
 */
@Slf4j
@Component
public class RedisLock {

    @Resource(name = "RedissonClient")
    private RedissonClient redissonClient;

    private static final String LOCK_TITLE = "redisLock_";


    public  void acquire(String lockName){
        String key = LOCK_TITLE + lockName;

        RLock  myLock = redissonClient.getLock(key);
        myLock.lock(50, TimeUnit.MILLISECONDS);
        log.info("====lock===");
    }

    public void release(String lockName){
        String key = LOCK_TITLE + lockName;
        RLock myLock = redissonClient.getLock(key);
        myLock.unlock();
        log.info("====unLock====");
    }


}
