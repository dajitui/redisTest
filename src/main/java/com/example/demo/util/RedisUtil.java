package com.example.demo.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RedisUtil {

    ConcurrentHashMap<String, Integer> concurrentHashMap = new ConcurrentHashMap();

    private static final String CACHE_NAME = "cache";

    @Resource
    private RedisTemplate redisTemplate;

    public String getRedis(String key) {
        if (concurrentHashMap.get(key) != null) {
            concurrentHashMap.put(key, concurrentHashMap.get(key) + 1);
            if(concurrentHashMap.get(key)>=1000){
                return getCache(key);
            }
        } else {
            concurrentHashMap.put(key, 1);
        }
        return (String) redisTemplate.opsForValue().get(key);
    }


    @Cacheable(value = CACHE_NAME, key = "redis+'_'+#key")
    public String getCache(String key){
        System.out.println("使用Cache");
        return (String) redisTemplate.opsForValue().get(key);
    }

}
