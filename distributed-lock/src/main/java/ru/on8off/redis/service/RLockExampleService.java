package ru.on8off.redis.service;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RLockExampleService {
    @Autowired
    private RedissonClient redissonClient;

    public boolean todo(String id) {
        RLock lock = redissonClient.getFairLock(id);
        if(lock.tryLock()){
            try{
                Thread.sleep(1000);
                return true;
            } catch (Exception e){
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
        return false;
    }
}
