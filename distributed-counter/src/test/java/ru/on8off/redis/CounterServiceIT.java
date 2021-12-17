package ru.on8off.redis;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.on8off.redis.service.CounterService;

import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CounterServiceIT {
    @Autowired
    CounterService counterService;

    @Test
    void test() throws InterruptedException {
        var id = "testId-" + UUID.randomUUID().toString();
        assertEquals(0L, counterService.get(id));
        var executor = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 100; i++) {
            executor.submit(()->counterService.addAndGet(id));
        }
        if (!executor.awaitTermination(1, TimeUnit.SECONDS)) {
            executor.shutdownNow();
        }
        assertEquals(100L, counterService.get(id));

    }
}