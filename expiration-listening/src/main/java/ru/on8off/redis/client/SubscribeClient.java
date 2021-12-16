package ru.on8off.redis.client;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

import java.util.concurrent.LinkedBlockingDeque;

@Component
public class SubscribeClient extends KeyExpirationEventMessageListener {
    @Autowired
    private LinkedBlockingDeque<String> eventQueue;

    public SubscribeClient(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }

    @SneakyThrows
    @Override
    public void onMessage(Message message, byte[] pattern) {
        eventQueue.put(new String(message.getBody()));
    }
}
