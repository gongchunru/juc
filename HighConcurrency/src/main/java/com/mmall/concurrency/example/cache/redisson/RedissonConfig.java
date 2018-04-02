package com.mmall.concurrency.example.cache.redisson;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author gongchunru
 * @email gongchunru@gmail.com
 * Date：2018/4/2 13:47
 */
@Configuration
public class RedissonConfig {


    @Bean(name = "RedissonClient")
    public RedissonClient jedisPool(@Value("${jedis.host}") String host,
                                    @Value("${jedis.port}") int port) {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://"+host+":"+port);
        return Redisson.create(config);
    }

}
