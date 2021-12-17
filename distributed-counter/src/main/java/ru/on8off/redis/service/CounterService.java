package ru.on8off.redis.service;

import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CounterService {
    @Autowired
    private RedissonClient redissonClient;

    public Long addAndGet(String id) {
        var aLong = redissonClient.getAtomicLong(id);
        return aLong.addAndGet(1);
    }

    public Long get(String id) {
        var aLong = redissonClient.getAtomicLong(id);
        return aLong.get();
    }
}
