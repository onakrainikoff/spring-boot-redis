package ru.on8off.redis.client;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Service;
import ru.on8off.redis.client.dto.Event;

import java.util.concurrent.LinkedBlockingDeque;

@Service
public class SubscribeClient implements MessageListener {
    @Autowired
    private LinkedBlockingDeque<Event> eventQueue;

    @SneakyThrows
    @Override
    public void onMessage(Message message, byte[] pattern) {
        eventQueue.put((Event)RedisSerializer.json().deserialize(message.getBody()));
    }
}
