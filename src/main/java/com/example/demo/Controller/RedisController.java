package com.example.demo.Controller;

import com.example.demo.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.CountDownLatch;

@RestController
public class RedisController {

    @Resource
    private RedisTemplate redisTemplate;

    @Autowired
    private RedisUtil redisUtil;

    @RequestMapping("/redis")
    public void getRedis() throws InterruptedException {
        long time=System.nanoTime();
        String key = "dajitui";
        redisTemplate.opsForValue().set(key, "0");
        CountDownLatch countDownLatch = new CountDownLatch(5000);
        for (int i = 0; i < 30000; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    countDownLatch.countDown();
                    System.out.println(redisUtil.getRedis(key));
                }
            }).start();
        }
        countDownLatch.await();
        long time1=System.nanoTime();
        System.out.println("耗时"+(time1-time));
    }

    @RequestMapping("/redis1")
    public void getRedis1() throws InterruptedException {
        long time=System.nanoTime();
        String key = "dajitui";
        redisTemplate.opsForValue().set(key, "0");
        CountDownLatch countDownLatch = new CountDownLatch(5000);
        for (int i = 0; i < 30000; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    countDownLatch.countDown();
                    System.out.println(redisTemplate.opsForValue().get(key));
                }
            }).start();
        }
        countDownLatch.await();
        long time1=System.nanoTime();
        System.out.println("耗时"+(time1-time));
    }


}
