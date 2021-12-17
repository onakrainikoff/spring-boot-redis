package ru.on8off.redis;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.on8off.redis.service.RRateLimiterExampleService;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class RRateLimiterExampleServiceIT {
    @Autowired
    RRateLimiterExampleService rRateLimiterExampleService;

    @Test
    void todo() throws InterruptedException {
        for (int i = 0; i < 3; i++) {
            var result =rRateLimiterExampleService.todo("id");
            System.out.println("i=" + i + " - " + result);
            assertTrue(result);
        }
        for (int i = 3; i < 5; i++) {
            var result =rRateLimiterExampleService.todo("id");
            System.out.println("i=" + i + " - " + result);
            assertFalse(result);
        }
        Thread.sleep(1000);
        for (int i = 5; i < 8; i++) {
            var result =rRateLimiterExampleService.todo("id");
            System.out.println("i=" + i + " - " + result);
            assertTrue(result);
        }
    }
}