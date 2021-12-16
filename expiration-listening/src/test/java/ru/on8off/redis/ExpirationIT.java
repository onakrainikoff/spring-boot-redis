package ru.on8off.redis;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;


import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ExpirationIT {
    @Autowired
    StringRedisTemplate stringRedisTemplate;
    @Autowired
    LinkedBlockingDeque<String> eventQueue;

    @Test
    void test() throws InterruptedException {
        stringRedisTemplate.opsForValue().set("testKey", "testValue", 1, TimeUnit.SECONDS);
        var key = eventQueue.poll(2, TimeUnit.SECONDS);
        assertNotNull(key);
        assertEquals("testKey", key);
    }
}