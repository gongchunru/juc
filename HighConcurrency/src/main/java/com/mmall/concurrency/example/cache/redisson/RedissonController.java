package com.mmall.concurrency.example.cache.redisson;

import com.mmall.concurrency.example.cache.RedisClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Key;
import java.util.concurrent.*;

/**
 * @author gongchunru
 * @email gongchunru@gmail.com
 * Date：2018/4/2 14:47
 */
@RestController
@RequestMapping("redisson")
@Slf4j
public class RedissonController {


    @Autowired
    RedisLock redisLock;


    @Autowired
    private RedisClient redisClient;



    @RequestMapping("test")
    public void test(){

        String key = "amount";

        try {
            redisClient.set(key,"0");
        } catch (Exception e) {
            e.printStackTrace();
        }

        ExecutorService executorService = new ThreadPoolExecutor(6, 50,
                60L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>());
        // 请求总数
        int clientTotal = 500;

        for (int i = 0; i < clientTotal; i++) {
            final int count = i;
            executorService.execute(() -> {
                try {
                    redisLock.acquire(key);

                    String temp = redisClient.get(key);
                    redisClient.set(key,String.valueOf(Integer.parseInt(temp) + 1));

                } catch (Exception e) {
                    log.error("exception", e);
                }finally {
                    redisLock.release(key);
                }
            });
        }

    }


}
