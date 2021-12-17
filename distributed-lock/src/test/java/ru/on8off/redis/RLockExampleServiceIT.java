package ru.on8off.redis;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.on8off.redis.service.RLockExampleService;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RLockExampleServiceIT {
    @Autowired
    RLockExampleService rLockExampleService;

    @Test
    void todo() throws InterruptedException, ExecutionException {
        var executor = Executors.newSingleThreadExecutor();
        var future = executor.submit(()->rLockExampleService.todo("id"));
        Thread.sleep(500);
        var result2 = rLockExampleService.todo("id");
        var result1 = future.get();
        if (!executor.awaitTermination(100, TimeUnit.MILLISECONDS)) {
            executor.shutdownNow();
        }
        assertTrue(result1);
        assertFalse(result2);
    }
}