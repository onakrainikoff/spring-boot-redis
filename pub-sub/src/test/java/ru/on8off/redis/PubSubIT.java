package ru.on8off.redis;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.on8off.redis.client.PublishClient;
import ru.on8off.redis.client.dto.Event;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PubSubIT {
    @Autowired
    PublishClient publishClient;
    @Autowired
    LinkedBlockingDeque<Event> eventQueue;

    @Test
    void test() throws InterruptedException {
        var event = new Event(1, "test");
        publishClient.publish(event);
        var result = eventQueue.poll(1, TimeUnit.SECONDS);
        assertEquals(event, result);
    }
}