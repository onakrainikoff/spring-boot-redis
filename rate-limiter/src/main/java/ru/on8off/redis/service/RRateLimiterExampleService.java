package ru.on8off.redis.service;

import org.redisson.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RRateLimiterExampleService {
    @Autowired
    private RedissonClient redissonClient;
    private final int RATE =3;
    private final int INTERVAL = 1;

    public boolean todo(String id) throws InterruptedException {
        RRateLimiter rateLimiter = redissonClient.getRateLimiter(id);
        var initResult = rateLimiter.trySetRateAsync(RateType.OVERALL, RATE, INTERVAL, RateIntervalUnit.SECONDS).await(1, TimeUnit.SECONDS);
        if(!initResult){
            throw new RuntimeException("Failed to get rate limiter for: " + id);
        }
        if(rateLimiter.tryAcquire(1)){
            Thread.sleep(100);
            return true;
        }
        return false;
    }
}
